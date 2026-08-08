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
import com.stellarelite.xingyuzhenlv.update.openUrl

@Composable
fun PaymentFeeScreen(onBack: () -> Unit = {}) {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) { Icon(Icons.Filled.ArrowBack, contentDescription = "返回") }
            Spacer(Modifier.width(8.dp))
            Text("支付通道服务费说明", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }

        HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Text(
                "PAYMENT CHANNEL SERVICE FEE",
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

            Text(
                "所有订单结账时，额外收取订单总价 6% 的综合支付通道服务费，用于覆盖第三方支付网关手续费、跨境结算成本与通道运维成本。",
                fontSize = 15.sp,
                lineHeight = 24.sp,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium
            )

            Spacer(Modifier.height(24.dp))

            NumberedRule(
                "1",
                "APP 页面展示的所有包车标价仅为车辆出行服务费，不包含支付渠道成本。"
            )
            Spacer(Modifier.height(16.dp))

            NumberedRule(
                "2",
                "所有订单结账时，将额外收取订单总价 6% 的综合支付通道服务费。"
            )
            Spacer(Modifier.height(16.dp))

            NumberedRule(
                "3",
                "该费用用于：第三方支付网关手续费、跨境结算成本、通道运维成本，由用户自愿承担。"
            )
            Spacer(Modifier.height(16.dp))

            NumberedRule(
                "4",
                "6% 服务费一旦被第三方支付平台收取，按退款政策规则执行，不一定随订单退款返还。"
            )
            Spacer(Modifier.height(16.dp))

            NumberedRule(
                "5",
                "本 APP 不直接收取现金、不触碰资金流，所有交易由第三方合规支付网关处理。"
            )

            Spacer(Modifier.height(32.dp))

            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))
            Spacer(Modifier.height(16.dp))

            Text("国际官网对应页面：", fontSize = 12.sp, color = MaterialTheme.colorScheme.outline)
            Spacer(Modifier.height(6.dp))
            OutlinedButton(
                onClick = { openUrl("https://www.stellarelite-xingyuzhenlv.com/#legal/payment") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("打开国际官网 →", fontSize = 13.sp, fontWeight = FontWeight.Medium)
            }

            Spacer(Modifier.height(14.dp))

            Text("中国官网对应页面：", fontSize = 12.sp, color = MaterialTheme.colorScheme.outline)
            Spacer(Modifier.height(6.dp))
            OutlinedButton(
                onClick = { openUrl("https://cn.stellarelite-xingyuzhenlv.com/#legal/payment") },
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
private fun NumberedRule(number: String, text: String) {
    Row {
        Text(
            "$number.  ",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
        Text(
            text,
            fontSize = 14.sp,
            lineHeight = 22.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f),
            modifier = Modifier.weight(1f)
        )
    }
}
