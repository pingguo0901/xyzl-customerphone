package com.stellarelite.xingyuzhenlv.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stellarelite.xingyuzhenlv.i18n.t
import com.stellarelite.xingyuzhenlv.ui.theme.ThemeManager
import com.stellarelite.xingyuzhenlv.ui.theme.ThemeMode

data class ThemeOption(val mode: ThemeMode, val emoji: String, val label: String, val desc: String)

@Composable
fun ThemeScreen(onBack: () -> Unit = {}) {
    val currentTheme = ThemeManager.currentMode.value

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) { Icon(Icons.Filled.ArrowBack, "返回") }
            Spacer(Modifier.width(8.dp))
            Text("主题", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(20.dp))

        val themes = listOf(
            ThemeOption(ThemeMode.SYSTEM, "☀️🌙", t("theme_system"), "自动跟随系统外观设置"),
            ThemeOption(ThemeMode.LIGHT, "☀️", t("theme_light"), "亮色背景，适合日间使用"),
            ThemeOption(ThemeMode.DARK, "🌙", t("theme_dark"), "暗色背景，护眼且省电")
        )

        themes.forEach { (mode, emoji, label, desc) ->
            val selected = currentTheme == mode

            Row(
                modifier = Modifier.fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .then(if (selected) Modifier.background(MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)) else Modifier)
                    .clickable { ThemeManager.setMode(mode) }
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(emoji, fontSize = 24.sp)
                Spacer(Modifier.width(14.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(label, fontSize = 16.sp, fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal)
                    Text(desc, fontSize = 12.sp, color = MaterialTheme.colorScheme.outline)
                }
                if (selected) {
                    Icon(Icons.Filled.Check, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                }
            }

            if (mode != themes.last().first) {
                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))
            }
        }
    }
}
