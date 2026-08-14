-- ============================================
-- 星域臻旅 - 客户端 App 读写权限 (RLS)
-- 客户端使用 anon key，需开放 INSERT/SELECT
-- ============================================

-- user_profile：注册写入 + 查询
DROP POLICY IF EXISTS "anon_insert_user_profile" ON public.user_profile;
CREATE POLICY "anon_insert_user_profile" ON public.user_profile FOR INSERT TO anon WITH CHECK (true);
DROP POLICY IF EXISTS "anon_select_user_profile" ON public.user_profile;
CREATE POLICY "anon_select_user_profile" ON public.user_profile FOR SELECT TO anon USING (true);

-- order_trips（单程接送）：下单写入 + 查询
DROP POLICY IF EXISTS "anon_insert_order_trips" ON public.order_trips;
CREATE POLICY "anon_insert_order_trips" ON public.order_trips FOR INSERT TO anon WITH CHECK (true);
DROP POLICY IF EXISTS "anon_select_order_trips" ON public.order_trips;
CREATE POLICY "anon_select_order_trips" ON public.order_trips FOR SELECT TO anon USING (true);

-- order_daily_trips（多日包车）：下单写入 + 查询
DROP POLICY IF EXISTS "anon_insert_order_daily_trips" ON public.order_daily_trips;
CREATE POLICY "anon_insert_order_daily_trips" ON public.order_daily_trips FOR INSERT TO anon WITH CHECK (true);
DROP POLICY IF EXISTS "anon_select_order_daily_trips" ON public.order_daily_trips;
CREATE POLICY "anon_select_order_daily_trips" ON public.order_daily_trips FOR SELECT TO anon USING (true);
