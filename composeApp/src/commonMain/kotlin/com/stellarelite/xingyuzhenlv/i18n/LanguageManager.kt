package com.stellarelite.xingyuzhenlv.i18n

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

object LanguageManager {
    val currentLang: MutableState<Language> = mutableStateOf(Language.SYSTEM)
    val resolvedLang: MutableState<Language> = mutableStateOf(Language.CHINESE)

    private var systemLang: Language = Language.CHINESE

    fun setLang(lang: Language) {
        currentLang.value = lang
        resolvedLang.value = if (lang == Language.SYSTEM) systemLang else lang
    }

    fun setSystemLocale(code: String) {
        systemLang = Language.fromCode(code)
        if (currentLang.value == Language.SYSTEM) {
            resolvedLang.value = systemLang
        }
    }
}

fun t(key: String): String = Strings.get(key, LanguageManager.resolvedLang.value)
