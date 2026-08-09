package com.stellarelite.xingyuzhenlv.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stellarelite.xingyuzhenlv.update.openLegalUrl

@Composable fun KYCScreen(onBack: () -> Unit = {}) {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) { Icon(Icons.Filled.ArrowBack, contentDescription = "返回") }
            Spacer(Modifier.width(8.dp))
            Text("KYC 实名认证", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }

        HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Text(
                "KNOW YOUR CUSTOMER (KYC) VERIFICATION",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.outline
            )
            Spacer(Modifier.height(4.dp))
            Text(
                "最后更新日期：2026年8月8日",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.7f)
            )

            Spacer(Modifier.height(24.dp))

            Text(
                "为遵守马来西亚《2001年反洗钱、反恐怖主义融资及非法活动收益法》（AMLA）及相关监管要求，星域臻旅对所有注册用户实施 KYC（了解你的客户）实名认证。",
                fontSize = 15.sp,
                lineHeight = 24.sp,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(Modifier.height(24.dp))

            // 1. 为什么需要 KYC
            SectionTitle("1. 为什么需要 KYC？")
            BulletList(listOf(
                "符合马来西亚国家银行（BNM）及金融监管机构的反洗钱合规要求",
                "确保账户安全，防止身份盗用和欺诈交易",
                "保障贵宾用户权益，确保服务款项的合法合规流转"
            ))

            Spacer(Modifier.height(24.dp))

            // 2. 需要提供的信息
            SectionTitle("2. 需要提供的信息")

            Spacer(Modifier.height(8.dp))
            HighlightCard("基础认证（适用于常规预订，订单金额 < RM 10,000）") {
                BulletList(listOf(
                    "真实姓名（与身份证明文件一致）",
                    "有效手机号码",
                    "电子邮箱"
                ))
            }

            Spacer(Modifier.height(12.dp))
            HighlightCard("进阶认证（适用于豪华车型/大额订单，订单金额 ≥ RM 10,000）") {
                BulletList(listOf(
                    "有效护照或身份证的扫描件/照片（马来西亚公民可用 MyKad）",
                    "近期地址证明（如水电费账单、银行月结单等，三个月内）",
                    "如为企业预订，需提供公司注册证明及授权代表的身份文件"
                ))
            }

            Spacer(Modifier.height(24.dp))

            // 3. 信息保护承诺
            SectionTitle("3. 信息保护承诺")
            BulletList(listOf(
                "您提交的所有 KYC 文件仅用于身份核实及合规目的",
                "所有文件经加密存储，仅授权人员可查阅",
                "我们不会将 KYC 信息用于营销目的，不会与第三方共享（法律法规要求除外）",
                "KYC 文件在服务关系终止后按法定保留期限（至少 6 年）存档，期满后安全销毁"
            ))

            Spacer(Modifier.height(24.dp))

            // 4. 审核时效
            SectionTitle("4. 审核时效")
            BulletList(listOf(
                "基础认证：即时完成",
                "进阶认证：提交完整文件后 1–2 个工作日内完成审核",
                "如文件不完整或不清晰，客服将联系您补交"
            ))

            Spacer(Modifier.height(24.dp))

            // 5. 不完成 KYC 的影响
            SectionTitle("5. 不完成 KYC 的影响")
            BulletList(listOf(
                "基础认证未完成 → 无法创建预订",
                "进阶认证未完成 → 无法预订豪华车型或大额订单",
                "我们保留对无法完成 KYC 的用户终止服务的权利"
            ))

            Spacer(Modifier.height(24.dp))

            // 6. 联系我们
            SectionTitle("6. 联系我们")
            Text(
                "如有任何关于 KYC 流程的疑问，请联系客服管家或发送邮件至 stellarelitexingyuzhenlv@gmail.com。",
                fontSize = 14.sp,
                lineHeight = 22.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f)
            )

            Spacer(Modifier.height(32.dp))

            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))
            Spacer(Modifier.height(16.dp))

            Text("国际官网对应页面：", fontSize = 12.sp, color = MaterialTheme.colorScheme.outline)
            Spacer(Modifier.height(6.dp))
            OutlinedButton(
                onClick = { com.stellarelite.xingyuzhenlv.update.openUrl("https://www.stellarelite-xingyuzhenlv.com/#legal/kyc") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("打开国际官网 →", fontSize = 13.sp, fontWeight = FontWeight.Medium)
            }

            Spacer(Modifier.height(14.dp))

            Text("中国官网对应页面：", fontSize = 12.sp, color = MaterialTheme.colorScheme.outline)
            Spacer(Modifier.height(6.dp))
            OutlinedButton(
                onClick = { com.stellarelite.xingyuzhenlv.update.openUrl("https://cn.stellarelite-xingyuzhenlv.com/#legal/kyc") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("打开中国官网 →", fontSize = 13.sp, fontWeight = FontWeight.Medium)
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
private fun HighlightCard(title: String, content: @Composable ColumnScope.() -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.06f),
        shape = MaterialTheme.shapes.small,
        border = ButtonDefaults.outlinedButtonBorder
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                title,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 6.dp)
            )
            content()
        }
    }
}

@Composable
private fun BulletList(items: List<String>) {
    Column {
        items.forEach { item ->
            Row(modifier = Modifier.padding(vertical = 3.dp)) {
                Text("·  ", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f))
                Text(
                    item,
                    fontSize = 14.sp,
                    lineHeight = 22.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f)
                )
            }
        }
    }
}
