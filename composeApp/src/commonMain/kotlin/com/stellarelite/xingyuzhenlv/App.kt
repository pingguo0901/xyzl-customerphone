package com.stellarelite.xingyuzhenlv

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.stellarelite.xingyuzhenlv.i18n.LanguageManager
import com.stellarelite.xingyuzhenlv.i18n.detectSystemLanguage
import com.stellarelite.xingyuzhenlv.ui.components.UpdateDialog
import com.stellarelite.xingyuzhenlv.ui.navigation.MainScreen
import com.stellarelite.xingyuzhenlv.ui.screens.SplashScreen
import com.stellarelite.xingyuzhenlv.ui.theme.XingyuZhenLvTheme
import com.stellarelite.xingyuzhenlv.update.UpdateManager
import com.stellarelite.xingyuzhenlv.update.SetStatusBarColor
import com.stellarelite.xingyuzhenlv.permission.rememberPermissionRequester
import androidx.compose.ui.graphics.Color

@Composable
fun App() {
    var showSplash by remember { mutableStateOf(true) }
    var permissionsRequested by remember { mutableStateOf(false) }

    val requestPermissions = rememberPermissionRequester(
        onAllGranted = { permissionsRequested = true },
        onPartialGranted = { permissionsRequested = true }
    )

    val sysLang = detectSystemLanguage()
    LaunchedEffect(Unit) {
        LanguageManager.setSystemLocale(sysLang)
    }

    XingyuZhenLvTheme {
        val bgColor = MaterialTheme.colorScheme.background
        SetStatusBarColor(color = bgColor)

        Surface(
            modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.statusBars),
            color = MaterialTheme.colorScheme.background
        ) {
            AnimatedContent(
                targetState = showSplash,
                transitionSpec = {
                    fadeIn(tween(300)) togetherWith fadeOut(tween(300))
                }
            ) { isSplash ->
                if (isSplash) {
                    SplashScreen(onFinished = {
                        showSplash = false
                        UpdateManager.checkForUpdate()
                        requestPermissions()
                    })
                } else {
                    MainScreen()
                }
            }

            // 更新弹窗
            UpdateDialog()
        }
    }
}
