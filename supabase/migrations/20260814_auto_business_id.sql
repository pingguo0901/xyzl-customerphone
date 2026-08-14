-- ============================================
-- 星域臻旅 - 业务ID自动生成
-- user_id    : 前缀 10 + 13位随机数字
-- driver_id  : 前缀 30 + 13位随机数字
-- vehicle_id : 前缀 50 + 13位随机数字
-- 格式: 前缀(2位) + 13位数字 = 15位
-- 说明: 插入时省略 ID 列(或传 DEFAULT)，数据库自动生成唯一ID
-- ============================================

-- 1. 通用唯一ID生成函数(前缀 + 13位随机数字，查重后返回)
CREATE OR REPLACE FUNCTION public.generate_prefixed_id(prefix text, tbl text, col text)
RETURNS text
LANGUAGE plpgsql
SECURITY DEFINER
AS $$
DECLARE
  new_id text;
  cnt int;
BEGIN
  LOOP
    new_id := prefix || lpad(floor(random() * 10000000000000)::bigint::text, 13, '0');
    EXECUTE format('SELECT count(*) FROM %I WHERE %I = $1', tbl, col) INTO cnt USING new_id;
    EXIT WHEN cnt = 0;
  END LOOP;
  RETURN new_id;
END;
$$;

-- 2. 三个表设置默认值(插入时自动生成)
ALTER TABLE public.user_profile
  ALTER COLUMN user_id SET DEFAULT public.generate_prefixed_id('10', 'user_profile', 'user_id');

ALTER TABLE public.driver_profile
  ALTER COLUMN driver_id SET DEFAULT public.generate_prefixed_id('30', 'driver_profile', 'driver_id');

ALTER TABLE public.vehicle_profile
  ALTER COLUMN vehicle_id SET DEFAULT public.generate_prefixed_id('50', 'vehicle_profile', 'vehicle_id');

-- 3. 唯一约束(防止重复，已存在则跳过)
DO $$
BEGIN
  IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'user_profile_user_id_key') THEN
    ALTER TABLE public.user_profile ADD CONSTRAINT user_profile_user_id_key UNIQUE (user_id);
  END IF;
  IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'driver_profile_driver_id_key') THEN
    ALTER TABLE public.driver_profile ADD CONSTRAINT driver_profile_driver_id_key UNIQUE (driver_id);
  END IF;
  IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'vehicle_profile_vehicle_id_key') THEN
    ALTER TABLE public.vehicle_profile ADD CONSTRAINT vehicle_profile_vehicle_id_key UNIQUE (vehicle_id);
  END IF;
END $$;
