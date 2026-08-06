package com.stellarelite.xingyuzhenlv.i18n

import androidx.compose.runtime.Composable
import platform.Foundation.NSLocale
import platform.Foundation.preferredLanguages

@Composable
actual fun detectSystemLanguage(): String {
    val lang = NSLocale.preferredLanguages.firstOrNull() as? String ?: "en"
    return lang.substringBefore("-").lowercase()
}
