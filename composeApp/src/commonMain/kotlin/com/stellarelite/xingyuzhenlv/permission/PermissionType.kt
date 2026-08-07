package com.stellarelite.xingyuzhenlv.permission

enum class PermissionType(val displayName: String, val description: String) {
    NOTIFICATION("通知", "用于接收行程更新和司机消息"),
    MICROPHONE("麦克风", "用于语音消息和通话"),
    CAMERA("相机", "用于拍照上传"),
    PHOTO_GALLERY("相册", "用于选择照片和视频上传"),
    LOCATION("位置信息", "用于定位和地图服务"),
    CONTACTS("通讯录", "用于快速填写联系人信息")
}
