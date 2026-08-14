-- ============================================
-- 星域臻旅 - 头像存储桶 + 邀请码自动生成
-- ============================================

-- 1. 创建 user_avatar 存储桶(公开读，头像直接可访问)
INSERT INTO storage.buckets (id, name, public)
VALUES ('user_avatar', 'user_avatar', true)
ON CONFLICT (id) DO NOTHING;

-- 2. 生成唯一随机邀请码(8位大写字母+数字)
CREATE OR REPLACE FUNCTION public.generate_referral_code()
RETURNS text
LANGUAGE plpgsql
SECURITY DEFINER
AS $$
DECLARE
  chars text := 'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';
  result text := '';
  i int;
  cnt int;
BEGIN
  LOOP
    result := '';
    FOR i IN 1..8 LOOP
      result := result || substr(chars, 1 + floor(random() * length(chars))::int, 1);
    END LOOP;
    SELECT count(*) INTO cnt FROM public.user_profile WHERE referral_code = result;
    EXIT WHEN cnt = 0;
  END LOOP;
  RETURN result;
END;
$$;

-- 3. referral_code 默认值(注册时自动生成)
ALTER TABLE public.user_profile
  ALTER COLUMN referral_code SET DEFAULT public.generate_referral_code();

-- 4. referral_code 唯一约束(防止重复)
DO $$
BEGIN
  IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'user_profile_referral_code_key') THEN
    ALTER TABLE public.user_profile ADD CONSTRAINT user_profile_referral_code_key UNIQUE (referral_code);
  END IF;
END $$;
