-- ============================================
-- 星域臻旅 - Storage 存储桶
-- 头像/消息媒体：公开读（public）
-- private 桶：私有（保存 private 表里的 url，如 IC/驾照/银行/SSM 等敏感文件）
-- ============================================

-- 1. 头像 & 消息媒体（公开）
INSERT INTO storage.buckets (id, name, public) VALUES
  ('driver_avatar', 'driver_avatar', true),
  ('butler_avatar', 'butler_avatar', true),
  ('staff_avatar', 'staff_avatar', true),
  ('company_avatar', 'company_avatar', true),
  ('message_photo_video', 'message_photo_video', true)
ON CONFLICT (id) DO NOTHING;

-- 2. 私密文档桶（非公开）
INSERT INTO storage.buckets (id, name, public) VALUES
  ('user_private', 'user_private', false),
  ('driver_private', 'driver_private', false),
  ('butler_private', 'butler_private', false),
  ('staff_private', 'staff_private', false),
  ('company_private', 'company_private', false)
ON CONFLICT (id) DO NOTHING;
