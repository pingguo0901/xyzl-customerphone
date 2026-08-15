-- ============================================
-- 星域臻旅 - 订单状态字段 status
-- order_trips / order_daily_trips 增加 status 列
-- 值: Confirm(确认预定) / Cancel(取消预定) / Pick-up(用户已上车) / Complete(用户已到达目的地)
-- ============================================

ALTER TABLE public.order_trips
  ADD COLUMN IF NOT EXISTS status text NOT NULL DEFAULT 'Confirm';

ALTER TABLE public.order_daily_trips
  ADD COLUMN IF NOT EXISTS status text NOT NULL DEFAULT 'Confirm';

-- 唯一允许的取值约束（防止脏数据）
DO $$
BEGIN
  IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'order_trips_status_check') THEN
    ALTER TABLE public.order_trips
      ADD CONSTRAINT order_trips_status_check
      CHECK (status IN ('Confirm','Cancel','Pick-up','Complete'));
  END IF;
  IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'order_daily_trips_status_check') THEN
    ALTER TABLE public.order_daily_trips
      ADD CONSTRAINT order_daily_trips_status_check
      CHECK (status IN ('Confirm','Cancel','Pick-up','Complete'));
  END IF;
END $$;
