package com.stellarelite.xingyuzhenlv.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.stellarelite.xingyuzhenlv.i18n.t
import com.stellarelite.xingyuzhenlv.update.UpdateManager

@Composable
fun UpdateDialog() {
    if (!UpdateManager.updateAvailable) return

    AlertDialog(
        onDismissRequest = { UpdateManager.updateAvailable = false },
        title = {
            Text(
                text = "🔄 ${t("update_available")}",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        },
        text = {
            Text(
                text = "${t("update_new_version")}: v${UpdateManager.serverVersion.versionName} (${UpdateManager.serverVersion.versionCode})\n\n" +
                       UpdateManager.serverVersion.changelog,
                fontSize = 14.sp
            )
        },
        confirmButton = {
            TextButton(onClick = { UpdateManager.updateAvailable = false }) {
                Text(t("update_later"))
            }
        },
        dismissButton = {
            TextButton(onClick = {
                // TODO: 跳转到下载页
                UpdateManager.updateAvailable = false
            }) {
                Text(t("update_now"))
            }
        }
    )
}
