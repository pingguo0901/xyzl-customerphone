package com.stellarelite.xingyuzhenlv.i18n

enum class Language(val code: String, val displayName: String, val flag: String) {
    SYSTEM("system", "跟随系统", "🌐"),
    CHINESE("zh", "中文", "🇨🇳"),
    ENGLISH("en", "English", "🇬🇧"),
    MALAY("ms", "Bahasa Melayu", "🇲🇾"),
    JAPANESE("ja", "日本語", "🇯🇵"),
    KOREAN("ko", "한국어", "🇰🇷");

    companion object {
        fun fromCode(code: String): Language = when (code) {
            "zh" -> CHINESE
            "en" -> ENGLISH
            "ms" -> MALAY
            "ja" -> JAPANESE
            "ko" -> KOREAN
            else -> ENGLISH
        }
    }
}
