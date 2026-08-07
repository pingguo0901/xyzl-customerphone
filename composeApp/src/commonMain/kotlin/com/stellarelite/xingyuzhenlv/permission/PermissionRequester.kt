package com.stellarelite.xingyuzhenlv.permission

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
expect fun rememberPermissionRequester(
    onAllGranted: () -> Unit,
    onPartialGranted: (List<PermissionType>) -> Unit
): () -> Unit
