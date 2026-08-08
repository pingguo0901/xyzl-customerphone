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

@Composable fun TermsOfServiceScreen(onBack: () -> Unit = {}) {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) { Icon(Icons.Filled.ArrowBack, contentDescription = "返回") }
            Spacer(Modifier.width(8.dp))
            Text("服务使用协议", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }

        HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Text(
                "TERMS OF SERVICE",
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
                "用户在本 APP 提交包车订单、完成付款、使用服务，即代表完全同意本服务协议全部条款。",
                fontSize = 15.sp,
                lineHeight = 24.sp,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium
            )

            Spacer(Modifier.height(20.dp))

            // 1. 服务定义
            SectionTitle("1. 服务定义")
            Text(
                "本平台为新马跨境包车预约服务平台，负责提供车辆调度、行程预约、出行对接服务。实际车辆与司乘服务由本地合规合作车队提供。",
                fontSize = 14.sp,
                lineHeight = 22.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f)
            )

            Spacer(Modifier.height(12.dp))

            HighlightBox("重要规则：所有用户下单前，必须完成护照+人脸 KYC 实名认证；未通过身份核验，无法创建包车订单，目的是防范诈骗、保障跨境出行安全。")

            Spacer(Modifier.height(20.dp))

            // 2. 用户责任
            SectionTitle("2. 用户责任")
            NumberedList(listOf(
                "用户保证所填写姓名、电话、地址、护照信息、KYC 提交资料真实、准确、有效；提交虚假证件、人脸资料，平台有权直接拒绝订单、封禁账号",
                "用户自行负责护照、签证、出入境资格，我方不代办签证与通关资质",
                "用户不得携带两国海关违禁物品乘车",
                "乘车人数不得超过车辆核定载客人数",
                "用户通过 APP 上传发送的照片、视频、语音内容，不得包含违法、暴力、骚扰内容",
                "因用户个人资料错误、证件失效、迟到、个人原因无法出行，由用户自行承担全部损失"
            ))

            Spacer(Modifier.height(20.dp))

            // 3. 价格与订单规则
            SectionTitle("3. 价格与订单规则")
            NumberedList(listOf(
                "APP 展示价格为包车基础服务费，不含路费、关卡费、停车费",
                "订单最终成交以支付成功 + KYC 身份核验全部通过为双重确认标准",
                "本平台收取 6% 支付通道服务费（第三方渠道成本），结账时统一叠加"
            ))

            Spacer(Modifier.height(20.dp))

            // 4. 行程变更
            SectionTitle("4. 行程变更")
            Text(
                "用户如需改期、改地点，需提前联系客服，最终能否变更以车辆调度空余状态为准。",
                fontSize = 14.sp,
                lineHeight = 22.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f)
            )

            Spacer(Modifier.height(20.dp))

            // 5. 责任限制
            SectionTitle("5. 责任限制")
            NumberedList(listOf(
                "堵车、边境排队、海关抽查、天气、政策变动等不可控外部因素，平台不承担延误赔偿责任",
                "支付交易、退款时效、资金流转全部由第三方支付网关负责，我方仅提供订单同步功能，不对支付系统故障承担责任",
                "用户自行管理手机系统权限，关闭权限造成定位失败、无法发送图片消息等问题，平台不承担责任",
                "因用户违规乘车、违禁物品、证件问题导致的罚款与滞留，我方不负责任"
            ))

            Spacer(Modifier.height(20.dp))

            // 6. 法律管辖
            SectionTitle("6. 法律管辖")
            Text(
                "本服务所有纠纷、争议适用马来西亚法律，处理地点为柔佛州。",
                fontSize = 14.sp,
                lineHeight = 22.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f)
            )

            Spacer(Modifier.height(32.dp))

            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))
            Spacer(Modifier.height(16.dp))
            Text("国际官网对应页面：", fontSize = 12.sp, color = MaterialTheme.colorScheme.outline)
            Text(
                "www.stellarelite-xingyuzhenlv.com/#legal/terms",
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
                "cn.stellarelite-xingyuzhenlv.com/#legal/terms",
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
