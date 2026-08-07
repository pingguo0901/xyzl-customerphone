package com.stellarelite.xingyuzhenlv.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stellarelite.xingyuzhenlv.i18n.Language
import com.stellarelite.xingyuzhenlv.i18n.LanguageManager
import com.stellarelite.xingyuzhenlv.i18n.t
import com.stellarelite.xingyuzhenlv.ui.theme.ThemeManager
import com.stellarelite.xingyuzhenlv.ui.theme.ThemeMode

@Composable
fun ProfileScreen(onLoginClick: () -> Unit = {}) {
    val scrollState = rememberScrollState()
    val currentVersion = "1.0.23"

    // 语言和主题
    val currentLang = LanguageManager.currentLang.value
    val currentTheme = ThemeManager.currentMode.value

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(scrollState).padding(horizontal = 16.dp).padding(top = 24.dp, bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // 头像区
        Row(
            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                .clickable(onClick = onLoginClick).padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(56.dp).clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.Person, null, tint = Color.White, modifier = Modifier.size(28.dp))
            }
            Spacer(Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text("登录 / 注册", fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
                Text("点击登录享受更多服务", fontSize = 12.sp, color = MaterialTheme.colorScheme.outline)
            }
            Icon(Icons.Filled.ChevronRight, null, tint = MaterialTheme.colorScheme.outline)
        }

        // 我的钱包
        ProfileMenuItem(Icons.Filled.Wallet, "我的钱包", "查看余额与交易")
        // 我的会员
        ProfileMenuItem(Icons.Filled.CardMembership, "我的会员", "会员等级与权益")
        // 个人信息
        ProfileMenuItem(Icons.Filled.ManageAccounts, "个人信息", "编辑个人资料")

        HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))

        // 隐私与安全
        ProfileMenuItem(Icons.Filled.Security, "隐私与安全", "密码、账号保护")
        // 通知设置
        ProfileMenuItem(Icons.Filled.NotificationsActive, "通知设置", "消息与推送管理")

        HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))

        // 语言
        ProfileMenuExpand(Icons.Filled.Translate, "语言", currentLang.displayName) {
            Language.entries.forEach { lang ->
                Row(
                    modifier = Modifier.fillMaxWidth().clickable { LanguageManager.setLang(lang) }.padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        if (lang == Language.SYSTEM) t("language_system") else lang.displayName,
                        fontSize = 14.sp,
                        fontWeight = if (currentLang == lang) FontWeight.Bold else FontWeight.Normal
                    )
                    if (currentLang == lang) {
                        Icon(Icons.Filled.Check, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                    }
                }
            }
        }

        // 主题
        ProfileMenuExpand(Icons.Filled.Palette, "主题", when (currentTheme) {
            ThemeMode.SYSTEM -> t("theme_system")
            ThemeMode.LIGHT -> t("theme_light")
            ThemeMode.DARK -> t("theme_dark")
        }) {
            listOf(ThemeMode.SYSTEM to t("theme_system"), ThemeMode.LIGHT to t("theme_light"), ThemeMode.DARK to t("theme_dark")).forEach { (mode, label) ->
                Row(
                    modifier = Modifier.fillMaxWidth().clickable { ThemeManager.setMode(mode) }.padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(label, fontSize = 14.sp, fontWeight = if (currentTheme == mode) FontWeight.Bold else FontWeight.Normal)
                    if (currentTheme == mode) {
                        Icon(Icons.Filled.Check, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                    }
                }
            }
        }

        // 版本
        ProfileMenuItem(Icons.Filled.Info, "版本", currentVersion, showArrow = false)
        // 关于我们
        ProfileMenuItem(Icons.Filled.Groups, "关于我们", "星域臻旅团队")
    }
}

@Composable
private fun ProfileMenuItem(
    icon: ImageVector, title: String, subtitle: String, showArrow: Boolean = true
) {
    Row(
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp))
            .clickable { }.padding(horizontal = 12.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(22.dp))
        Spacer(Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, fontSize = 15.sp, fontWeight = FontWeight.Medium)
            Text(subtitle, fontSize = 12.sp, color = MaterialTheme.colorScheme.outline)
        }
        if (showArrow) {
            Icon(Icons.Filled.ChevronRight, null, tint = MaterialTheme.colorScheme.outline, modifier = Modifier.size(18.dp))
        } else {
            Text(subtitle, fontSize = 13.sp, color = MaterialTheme.colorScheme.outline)
        }
    }
}

@Composable
private fun ProfileMenuExpand(
    icon: ImageVector, title: String, currentValue: String, content: @Composable ColumnScope.() -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp))
            .clickable { expanded = !expanded }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(22.dp))
            Spacer(Modifier.width(14.dp))
            Text(title, fontSize = 15.sp, fontWeight = FontWeight.Medium, modifier = Modifier.weight(1f))
            Text(currentValue, fontSize = 13.sp, color = MaterialTheme.colorScheme.outline)
            Spacer(Modifier.width(4.dp))
            Icon(
                if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                null, tint = MaterialTheme.colorScheme.outline, modifier = Modifier.size(20.dp)
            )
        }
        if (expanded) {
            Column {
                HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.1f), modifier = Modifier.padding(horizontal = 12.dp))
                content()
            }
        }
    }
}
