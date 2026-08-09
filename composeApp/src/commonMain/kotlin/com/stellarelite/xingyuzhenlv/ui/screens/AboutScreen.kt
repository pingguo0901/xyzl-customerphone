package com.stellarelite.xingyuzhenlv.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stellarelite.xingyuzhenlv.i18n.t
import com.stellarelite.xingyuzhenlv.update.openUrl

@Composable fun AboutScreen(onBack: () -> Unit = {}) {
    Column(modifier = Modifier.fillMaxSize()) {
        // Top Bar
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) { Icon(Icons.Filled.ArrowBack, contentDescription = "返回") }
            Spacer(Modifier.width(8.dp))
            Text(t("about_title"), fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }

        HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))

        // Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Text(
                "ABOUT STELLARELITE",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.outline
            )
            Spacer(Modifier.height(4.dp))
            Text(
                "生效日期：2026年8月8日",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.7f)
            )

            Spacer(Modifier.height(24.dp))

            // Brand intro
            Text(
                t("about_intro"),
                fontSize = 15.sp,
                lineHeight = 24.sp,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(Modifier.height(20.dp))

            // 唯一业务
            SectionTitle("唯一业务")
            Text(
                "马来西亚柔佛新山 ↔ 新加坡 新马跨境包车出行预订服务。",
                fontSize = 14.sp,
                lineHeight = 22.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f)
            )

            Spacer(Modifier.height(20.dp))

            // APP服务范围
            SectionTitle(t("about_services"))
            val services = t("about_services_list").split("/")

            Spacer(Modifier.height(20.dp))

            // 安全须知
            SectionTitle("安全须知")
            Text(
                "为防范诈骗风险，使用本 APP 下单预订包车服务，用户必须完成护照与人脸活体身份核验。",
                fontSize = 14.sp,
                lineHeight = 22.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f)
            )

            Spacer(Modifier.height(20.dp))

            // 设备权限
            SectionTitle("设备权限")
            Text(
                "本 APP 会申请设备权限：推送通知、位置、相机、麦克风、通讯录、相册照片与视频，用于出行沟通、接驾定位、身份核验。",
                fontSize = 14.sp,
                lineHeight = 22.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f)
            )

            Spacer(Modifier.height(20.dp))

            // 支付声明
            SectionTitle("支付声明")
            Text(
                "所有线上支付流程由第三方合规支付网关处理，本 APP 不收集、不储存、不处理用户银行卡、电子钱包等敏感支付资料。",
                fontSize = 14.sp,
                lineHeight = 22.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f)
            )

            Spacer(Modifier.height(20.dp))

            // 联系方式
            SectionTitle(t("about_contact"))
            Text("WhatsApp：+65 8194 5601", fontSize = 14.sp, lineHeight = 22.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f))
            Text("电子邮箱：stellarelitexingyuzhenlv@gmail.com", fontSize = 14.sp, lineHeight = 22.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f))
            Text(t("about_location"), fontSize = 14.sp, lineHeight = 22.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f))

            Spacer(Modifier.height(32.dp))

            // Bottom: official website links
            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))
            Spacer(Modifier.height(16.dp))

            Text(
                t("about_intl_site"),
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.outline
            )
            Spacer(Modifier.height(6.dp))
            OutlinedButton(
                onClick = { openUrl("https://www.stellarelite-xingyuzhenlv.com/#legal/about") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    "打开国际官网 →",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(Modifier.height(14.dp))

            Text(
                t("about_cn_site"),
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.outline
            )
            Spacer(Modifier.height(6.dp))
            OutlinedButton(
                onClick = { openUrl("https://cn.stellarelite-xingyuzhenlv.com/#legal/about") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    "打开中国官网 →",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(Modifier.height(32.dp))
        }
    }
}

@Composable
private fun SectionTitle(title: String) {
    Text(
        title,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
private fun BulletList(items: List<String>) {
    Column {
        items.forEach { item ->
            Row(modifier = Modifier.padding(vertical = 3.dp)) {
                Text("·  ", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f))
                Text(item, fontSize = 14.sp, lineHeight = 22.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f))
            }
        }
    }
}
