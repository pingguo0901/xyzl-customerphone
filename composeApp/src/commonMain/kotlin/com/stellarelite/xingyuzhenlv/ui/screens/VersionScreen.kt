package com.stellarelite.xingyuzhenlv.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stellarelite.xingyuzhenlv.update.UpdateManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

@Composable
fun VersionScreen(onBack: () -> Unit = {}) {
    val scope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(false) }
    var latestVersion by remember { mutableStateOf("") }
    var latestVersionCode by remember { mutableIntStateOf(0) }
    var showResult by remember { mutableStateOf(false) }
    var isUpToDate by remember { mutableStateOf(false) }

    val currentVersion = "1.0.49"
    val currentVersionCode = UpdateManager.CURRENT_VERSION_CODE

    fun checkUpdate() {
        scope.launch {
            isLoading = true
            try {
                val json = withContext(Dispatchers.Default) {
                    com.stellarelite.xingyuzhenlv.update.fetchUrl(UpdateManager.VERSION_URL)
                }
                val version = Json { ignoreUnknownKeys = true }.decodeFromString<com.stellarelite.xingyuzhenlv.update.ServerVersion>(json)
                latestVersion = version.versionName
                latestVersionCode = version.versionCode
                isUpToDate = version.versionCode <= currentVersionCode
                showResult = true
            } catch (_: Exception) {
                latestVersion = "检查失败"
            }
            isLoading = false
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) { Icon(Icons.Filled.ArrowBack, "返回") }
            Spacer(Modifier.width(8.dp))
            Text("版本", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(32.dp))

        // 当前版本卡片
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
        ) {
            Column(Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("当前版本", fontSize = 13.sp, color = MaterialTheme.colorScheme.outline)
                Spacer(Modifier.height(4.dp))
                Text("v$currentVersion", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                Text("Version Code: $currentVersionCode", fontSize = 12.sp, color = MaterialTheme.colorScheme.outline)
            }
        }

        Spacer(Modifier.height(16.dp))

        // 最新版本
        if (showResult) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            ) {
                Column(Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("最新版本", fontSize = 13.sp, color = MaterialTheme.colorScheme.outline)
                    Spacer(Modifier.height(4.dp))
                    Text("v$latestVersion", fontSize = 28.sp, fontWeight = FontWeight.Bold,
                        color = if (isUpToDate) Color(0xFF4CAF50) else MaterialTheme.colorScheme.primary)
                    if (latestVersionCode > 0) Text("Version Code: $latestVersionCode", fontSize = 12.sp, color = MaterialTheme.colorScheme.outline)
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        // 检查版本按钮
        Button(
            onClick = { checkUpdate() },
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape = RoundedCornerShape(14.dp),
            enabled = !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Color.White, strokeWidth = 2.dp)
                Spacer(Modifier.width(10.dp))
                Text("检查中...", fontSize = 16.sp)
            } else {
                Icon(Icons.Filled.Refresh, null, modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(10.dp))
                Text("检查版本", fontSize = 16.sp, fontWeight = FontWeight.Medium)
            }
        }

        Spacer(Modifier.height(16.dp))

        // 结果弹窗
        if (showResult) {
            if (isUpToDate) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF4CAF50).copy(alpha = 0.1f))
                ) {
                    Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.CheckCircle, null, tint = Color(0xFF4CAF50), modifier = Modifier.size(22.dp))
                        Spacer(Modifier.width(10.dp))
                        Text("已是最新版本（v$currentVersion）", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                    }
                }
            } else {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFF9800).copy(alpha = 0.1f))
                ) {
                    Column(Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Filled.SystemUpdate, null, tint = Color(0xFFFF9800), modifier = Modifier.size(22.dp))
                            Spacer(Modifier.width(10.dp))
                            Text("发现新版本 v$latestVersion", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color(0xFFFF9800))
                        }
                        Spacer(Modifier.height(8.dp))
                        Button(
                            onClick = { UpdateManager.startDownload() },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("立即更新", fontSize = 14.sp)
                        }
                    }
                }
            }
        }
    }
}
