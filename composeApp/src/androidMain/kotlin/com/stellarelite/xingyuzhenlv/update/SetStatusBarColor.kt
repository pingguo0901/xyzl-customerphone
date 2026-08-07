package com.stellarelite.xingyuzhenlv.update

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

@Composable
actual fun SetStatusBarColor(color: Color) {
    val context = AppContextHolder.context ?: return
    val window = (context as? android.app.Activity)?.window ?: return
    SideEffect {
        window.statusBarColor = color.toArgb()
    }
}
