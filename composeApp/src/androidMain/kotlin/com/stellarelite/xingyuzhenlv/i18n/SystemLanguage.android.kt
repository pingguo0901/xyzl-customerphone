package com.stellarelite.xingyuzhenlv.i18n

import androidx.compose.runtime.Composable
import java.util.Locale

@Composable
actual fun detectSystemLanguage(): String {
    val locale = Locale.getDefault()
    return locale.language
}
