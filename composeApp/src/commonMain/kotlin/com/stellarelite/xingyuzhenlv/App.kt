package com.stellarelite.xingyuzhenlv

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
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

@Composable
fun App() {
    var showSplash by remember { mutableStateOf(true) }

    val sysLang = detectSystemLanguage()
    LaunchedEffect(Unit) {
        LanguageManager.setSystemLocale(sysLang)
    }

    XingyuZhenLvTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
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
