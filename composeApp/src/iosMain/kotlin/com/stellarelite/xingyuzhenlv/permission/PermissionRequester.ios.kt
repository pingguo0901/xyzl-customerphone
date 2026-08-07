package com.stellarelite.xingyuzhenlv.permission

import androidx.compose.runtime.Composable

@Composable
actual fun rememberPermissionRequester(
    onAllGranted: () -> Unit,
    onPartialGranted: (List<PermissionType>) -> Unit
): () -> Unit {
    // iOS 权限由系统在首次使用时自动弹出，这里直接回调
    return { onAllGranted() }
}
