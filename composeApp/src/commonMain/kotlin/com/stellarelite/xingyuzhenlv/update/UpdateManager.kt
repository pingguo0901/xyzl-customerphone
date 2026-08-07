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
    const val CURRENT_VERSION_CODE = 10  // 跟着 build.gradle.kts 同步更新
    private const val VERSION_URL = "https://raw.githubusercontent.com/pingguo0901/xyzl-customerphone/main/version.json"

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

@Composable
expect fun SetStatusBarColor(color: androidx.compose.ui.graphics.Color)
