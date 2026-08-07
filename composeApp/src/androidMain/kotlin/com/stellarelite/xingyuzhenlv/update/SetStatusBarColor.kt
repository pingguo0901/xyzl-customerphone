package com.stellarelite.xingyuzhenlv.update

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

@Composable
actual fun SetStatusBarColor(color: Color) {
    val activity = AppContextHolder.activity ?: return
    SideEffect {
        activity.window.statusBarColor = color.toArgb()
    }
}
