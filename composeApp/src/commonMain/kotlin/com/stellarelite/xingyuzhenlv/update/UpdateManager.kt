package com.stellarelite.xingyuzhenlv.update

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.Composable
import kotlinx.coroutines.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class ServerVersion(
    val versionCode: Int = 0,
    val versionName: String = "",
    val apkUrl: String = "",
    val changelog: String = ""
)

object UpdateManager {
    const val CURRENT_VERSION_CODE = 51  // 跟着 build.gradle.kts 同步更新
    internal const val VERSION_URL = "https://raw.githubusercontent.com/pingguo0901/xyzl-customerphone/main/version.json"

    var updateAvailable by mutableStateOf(false)
    var serverVersion by mutableStateOf(ServerVersion())
    var isDownloading by mutableStateOf(false)

    fun checkForUpdate() {
        CoroutineScope(Dispatchers.Default).launch {
            try {
                val json = fetchUrl(VERSION_URL)
                val version = Json { ignoreUnknownKeys = true }.decodeFromString<ServerVersion>(json)
                if (version.versionCode > CURRENT_VERSION_CODE) {
                    serverVersion = version
                    updateAvailable = true
                }
            } catch (_: Exception) {
                // 网络问题或解析失败，静默处理
            }
        }
    }

    fun startDownload() {
        if (serverVersion.apkUrl.isNotBlank()) {
            isDownloading = true
            updateAvailable = false
            downloadApk(serverVersion.apkUrl)
        }
    }
}

expect suspend fun fetchUrl(url: String): String
expect fun downloadApk(url: String)
expect fun openUrl(url: String)

/** 根据系统语言智能跳转国际/中国官网法律页面 */
fun openLegalUrl(section: String) {
    val isChinese = com.stellarelite.xingyuzhenlv.i18n.LanguageManager.resolvedLang.value == com.stellarelite.xingyuzhenlv.i18n.Language.CHINESE
    val url = if (isChinese) "https://cn.stellarelite-xingyuzhenlv.com/#legal/$section"
              else "https://www.stellarelite-xingyuzhenlv.com/#legal/$section"
    openUrl(url)
}

@Composable
expect fun SetStatusBarColor(color: androidx.compose.ui.graphics.Color)
