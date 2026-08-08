package com.stellarelite.xingyuzhenlv.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable fun RefundPolicyScreen(onBack: () -> Unit = {}) {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) { Icon(Icons.Filled.ArrowBack, contentDescription = "返回") }
            Spacer(Modifier.width(8.dp))
            Text("预订、退款与取消政策", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }

        HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Text(
                "BOOKING, REFUND & CANCELLATION POLICY",
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

            // 一、预订流程
            SectionTitle("一、预订流程")
            NumberedList(listOf(
                "通过官网、App 或客服渠道提交出行需求（日期、人数、车型、行程等）",
                "客服/管家确认可用车辆与报价，生成预订确认函",
                "用户确认预订并完成付款",
                "收到付款后，发送最终出行确认书（含司导信息、车牌号、接送时间地点）",
                "出行前 24 小时，司导将通过 WhatsApp/微信与用户确认接载细节"
            ))

            Spacer(Modifier.height(24.dp))

            // 二、取消与退款规则
            SectionTitle("二、取消与退款规则")

            HighlightCard {
                Column {
                    RefundRule("1", "出行前 48 小时及以上取消", "可申请退款订单基础金额全款。6% 支付通道服务费为第三方收取，不予退还。")
                    Spacer(Modifier.height(12.dp))
                    RefundRule("2", "出行前 24–48 小时内取消", "退还订单基础金额 70%。6% 支付通道服务费不予退还。")
                    Spacer(Modifier.height(12.dp))
                    RefundRule("3", "出行前 24 小时以内取消 / 临时弃单", "不予退款。")
                    Spacer(Modifier.height(12.dp))
                    RefundRule("4", "用户个人原因无法过关、迟到、证件问题", "不予退款。")
                    Spacer(Modifier.height(12.dp))
                    RefundRule("5", "平台原因无法履约", "全额退款（包含用户支付的通道服务费）。")
                }
            }

            Spacer(Modifier.height(24.dp))

            // 三、退款时效
            SectionTitle("三、退款时效")
            Text(
                "审核通过后，退款将在 7–14 个工作日内原路退回，最终到账时间以支付网关与银行结算时间为准。",
                fontSize = 14.sp,
                lineHeight = 22.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f)
            )
            Spacer(Modifier.height(8.dp))
            Text(
                "所有退款申请需通过官方客服渠道提交。",
                fontSize = 14.sp,
                lineHeight = 22.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f)
            )

            Spacer(Modifier.height(16.dp))

            HighlightBox("备注：KYC 身份核验仅用于账号身份确认，核验成功不等于保证出行通关，出入境责任由用户本人承担。")

            Spacer(Modifier.height(32.dp))

            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))
            Spacer(Modifier.height(16.dp))
            Text("国际官网对应页面：", fontSize = 12.sp, color = MaterialTheme.colorScheme.outline)
            Text(
                "www.stellarelite-xingyuzhenlv.com/#legal/refund",
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Medium
            )

            Spacer(Modifier.height(10.dp))
            Text(
                "中国官网对应页面：",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.outline
            )
            Text(
                "cn.stellarelite-xingyuzhenlv.com/#legal/refund",
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Medium
            )

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
        modifier = Modifier.padding(bottom = 10.dp)
    )
}

@Composable
private fun HighlightBox(text: String) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.06f),
        shape = MaterialTheme.shapes.small,
        border = ButtonDefaults.outlinedButtonBorder
    ) {
        Text(
            text,
            fontSize = 13.sp,
            lineHeight = 20.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f),
            modifier = Modifier.padding(12.dp)
        )
    }
}

@Composable
private fun HighlightCard(content: @Composable ColumnScope.() -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.06f),
        shape = MaterialTheme.shapes.small,
        border = ButtonDefaults.outlinedButtonBorder
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            content()
        }
    }
}

@Composable
private fun RefundRule(number: String, condition: String, policy: String) {
    Row {
        Text(
            "$number.  ",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                condition,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                lineHeight = 20.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(Modifier.height(2.dp))
            Text(
                policy,
                fontSize = 13.sp,
                lineHeight = 20.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
            )
        }
    }
}

@Composable
private fun NumberedList(items: List<String>) {
    Column {
        items.forEachIndexed { index, item ->
            Row(modifier = Modifier.padding(vertical = 3.dp)) {
                Text(
                    "${index + 1}.  ",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f),
                    fontWeight = FontWeight.Medium
                )
                Text(
                    item,
                    fontSize = 14.sp,
                    lineHeight = 22.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f),
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}
