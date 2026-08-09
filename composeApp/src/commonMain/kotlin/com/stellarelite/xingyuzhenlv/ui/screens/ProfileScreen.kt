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
fun ProfileScreen(
    onLoginClick: () -> Unit = {},
    onWalletClick: () -> Unit = {},
    onPersonalInfoClick: () -> Unit = {},
    onPrivacyClick: () -> Unit = {},
    onCurrencyClick: () -> Unit = {},
    onLanguageClick: () -> Unit = {},
    onThemeClick: () -> Unit = {},
    onVersionClick: () -> Unit = {},
    onAboutClick: () -> Unit = {},
    onPrivacyPolicyClick: () -> Unit = {},
    onTermsOfServiceClick: () -> Unit = {},
    onRefundPolicyClick: () -> Unit = {},
    onPaymentFeeClick: () -> Unit = {},
    onKYCClick: () -> Unit = {}
) {
    val scrollState = rememberScrollState()
    val currentVersion = "1.0.47"

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
                Text(t("login_register"), fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
                Text(t("login_hint"), fontSize = 12.sp, color = MaterialTheme.colorScheme.outline)
            }
            Icon(Icons.Filled.ChevronRight, null, tint = MaterialTheme.colorScheme.outline)
        }

        // 我的钱包
        ProfileMenuItem(Icons.Filled.Wallet, t("my_wallet"), t("wallet_subtitle"), onClick = onWalletClick)
        // 我的会员
        ProfileMenuItem(Icons.Filled.CardMembership, t("my_membership"), t("membership_subtitle"))
        // 个人信息
        ProfileMenuItem(Icons.Filled.ManageAccounts, t("personal_info"), t("personal_info_subtitle"), onClick = onPersonalInfoClick)

        HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))

        // 隐私与安全
        ProfileMenuItem(Icons.Filled.Security, t("privacy_security"), t("privacy_security_subtitle"), onClick = onPrivacyClick)
        // 通知设置
        ProfileMenuItem(Icons.Filled.NotificationsActive, t("notification_settings"), t("notification_subtitle"))

        // 货币
        ProfileMenuItem(Icons.Filled.CurrencyExchange, t("currency_label"), t("currency_subtitle"), onClick = onCurrencyClick)

        HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))

        // 语言
        ProfileMenuItem(Icons.Filled.Translate, t("language_settings"), t("language_settings_subtitle"), onClick = onLanguageClick)

        // 主题
        ProfileMenuItem(Icons.Filled.Palette, t("theme_settings"), t("theme_settings_subtitle"), onClick = onThemeClick)

        // 版本
        ProfileMenuItem(Icons.Filled.Info, t("version"), currentVersion, showArrow = true, onClick = onVersionClick)
        // 关于我们
        ProfileMenuItem(Icons.Filled.Groups, t("about_us"), t("about_us_subtitle"), onClick = onAboutClick)

        HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))

        // 隐私政策
        ProfileMenuItem(Icons.Outlined.PrivacyTip, t("privacy_policy_menu"), t("privacy_policy_menu_subtitle"), onClick = onPrivacyPolicyClick)
        // 服务使用协议
        ProfileMenuItem(Icons.Outlined.Description, t("terms_of_service_menu"), t("terms_of_service_menu_subtitle"), onClick = onTermsOfServiceClick)
        // 预约、退款与取消政策
        ProfileMenuItem(Icons.Outlined.ReceiptLong, t("refund_policy_menu"), t("refund_policy_menu_subtitle"), onClick = onRefundPolicyClick)
        // 支付通道服务费说明
        ProfileMenuItem(Icons.Outlined.Payment, t("payment_fee_menu"), t("payment_fee_menu_subtitle"), onClick = onPaymentFeeClick)
        // KYC实名认证
        ProfileMenuItem(Icons.Outlined.VerifiedUser, t("kyc_menu"), t("kyc_menu_subtitle"), onClick = onKYCClick)
    }
}

@Composable
private fun ProfileMenuItem(
    icon: ImageVector, title: String, subtitle: String, showArrow: Boolean = true, onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick).padding(horizontal = 12.dp, vertical = 14.dp),
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
