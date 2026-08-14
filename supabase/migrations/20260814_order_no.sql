-- ============================================
-- 星域臻旅 - 订单号自动生成
-- order_trips.order_no        : 前缀 pw + 13位随机数字
-- order_daily_trips.order_no  : 前缀 pd + 13位随机数字
-- ============================================

-- 1. order_daily_trips 补 order_no 字段
ALTER TABLE public.order_daily_trips ADD COLUMN IF NOT EXISTS order_no text;

-- 2. 订单号自动生成默认值
ALTER TABLE public.order_trips
  ALTER COLUMN order_no SET DEFAULT public.generate_prefixed_id('pw', 'order_trips', 'order_no');

ALTER TABLE public.order_daily_trips
  ALTER COLUMN order_no SET DEFAULT public.generate_prefixed_id('pd', 'order_daily_trips', 'order_no');

-- 3. 订单号唯一约束(防止重复)
DO $$
BEGIN
  IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'order_trips_order_no_key') THEN
    ALTER TABLE public.order_trips ADD CONSTRAINT order_trips_order_no_key UNIQUE (order_no);
  END IF;
  IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'order_daily_trips_order_no_key') THEN
    ALTER TABLE public.order_daily_trips ADD CONSTRAINT order_daily_trips_order_no_key UNIQUE (order_no);
  END IF;
END $$;
