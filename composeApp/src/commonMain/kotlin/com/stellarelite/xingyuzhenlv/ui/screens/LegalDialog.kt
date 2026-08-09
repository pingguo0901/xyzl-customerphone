package com.stellarelite.xingyuzhenlv.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/** 统一的法律协议内容弹窗 */
@Composable
fun LegalContentDialog(section: String, onDismiss: () -> Unit) {
    val (title, content) = when (section) {
        "terms" -> Pair("服务使用协议", @Composable { TermsOfServiceContent() })
        "privacy" -> Pair("隐私政策", @Composable { PrivacyPolicyContent() })
        "refund" -> Pair("预订退款及取消政策", @Composable { RefundPolicyContent() })
        "payment" -> Pair("支付通道服务费说明", @Composable { PaymentFeeContent() })
        else -> Pair("", @Composable {})
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title, fontWeight = FontWeight.Bold) },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 500.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                content()
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) { Text("关闭") }
        }
    )
}

@Composable
private fun TermsOfServiceContent() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            "用户在本 APP 提交包车订单、完成付款、使用服务，即代表完全同意本服务协议全部条款。",
            fontSize = 14.sp, lineHeight = 22.sp, fontWeight = FontWeight.Medium
        )
        LegalSection("1. 服务定义") {
            Text("本平台为新马跨境包车预约服务平台，负责提供车辆调度、行程预约、出行对接服务。实际车辆与司乘服务由本地合规合作车队提供。",
                fontSize = 13.sp, lineHeight = 20.sp)
        }
        LegalSection("2. 用户责任") {
            LegalList(listOf(
                "用户保证所填写姓名、电话、地址、护照信息、KYC 提交资料真实、准确、有效",
                "用户自行负责护照、签证、出入境资格",
                "用户不得携带两国海关违禁物品乘车",
                "乘车人数不得超过车辆核定载客人数",
                "因个人资料错误、证件失效、迟到等原因无法出行，由用户自行承担全部损失"
            ))
        }
        LegalSection("3. 价格与订单规则") {
            LegalList(listOf(
                "APP 展示价格为包车基础服务费，不含路费、关卡费、停车费",
                "订单最终成交以支付成功 + KYC 身份核验全部通过为双重确认标准",
                "本平台收取 6% 支付通道服务费，结账时统一叠加"
            ))
        }
        LegalSection("4. 行程变更") {
            Text("用户如需改期、改地点，需提前联系客服，最终能否变更以车辆调度空余状态为准。",
                fontSize = 13.sp, lineHeight = 20.sp)
        }
        LegalSection("5. 责任限制") {
            LegalList(listOf(
                "堵车、边境排队、海关抽查、天气、政策变动等不可控因素，平台不承担延误赔偿责任",
                "支付交易、退款时效、资金流转由第三方支付网关负责",
                "因用户违规乘车、违禁物品、证件问题导致的罚款与滞留，平台不承担责任"
            ))
        }
        LegalSection("6. 法律管辖") {
            Text("本服务所有纠纷、争议适用马来西亚法律，处理地点为柔佛州。",
                fontSize = 13.sp, lineHeight = 20.sp)
        }
        Text("所有用户下单前，必须完成护照+人脸 KYC 实名认证；未通过身份核验，无法创建包车订单。",
            fontSize = 13.sp, lineHeight = 20.sp, fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
private fun PrivacyPolicyContent() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("本隐私政策依据马来西亚《个人数据保护法 PDPA 2010（2024修订案）》制定。",
            fontSize = 14.sp, lineHeight = 22.sp, fontWeight = FontWeight.Medium)
        LegalHighlight("人脸生物识别数据属于敏感个人数据，提交 KYC 即代表你明确同意我们处理你的护照资料及人脸生物信息。不完成身份核验，将无法提交包车订单。")
        LegalSection("1. 收集的个人信息") {
            LegalList(listOf(
                "用户姓名、手机号码、电子邮箱、微信号",
                "出行信息：接送地点、出行日期、时间、乘车人数",
                "KYC 身份核验资料：护照照片、护照 MRZ 信息、人脸活体自拍（生物识别数据）",
                "跨境通关所需：乘客护照资料",
                "设备权限：位置（接驾定位）、相机（KYC拍摄）、相册、麦克风、通讯录、通知推送",
                "基础设备日志：设备型号、系统版本、崩溃日志"
            ))
        }
        LegalHighlight("信用卡、电子钱包、支付密码等支付敏感信息，本平台全程不收集、不保存、不获取，全部由第三方支付网关独立处理。")
        LegalSection("2. 信息使用目的") {
            LegalList(listOf(
                "完成 KYC 实人身份核验，防范账号冒用、订单诈骗",
                "确认、安排、执行用户的新马跨境包车行程",
                "获取位置、接收用户上传的照片视频、语音消息",
                "推送订单变更、行程提醒通知",
                "满足新马两国边境通关报备要求"
            ))
        }
        LegalSection("3. 信息共享范围") {
            LegalList(listOf(
                "合作车辆司机：仅获取行程所需最低信息，不传递人脸生物数据",
                "马来西亚、新加坡海关依法所需通关资料",
                "第三方支付网关（仅支付流程必要数据）"
            ))
            Text("本平台绝不会出售、出租、商用用户个人资料、护照、人脸生物识别数据。",
                fontSize = 13.sp, lineHeight = 20.sp, fontWeight = FontWeight.Medium)
        }
        LegalSection("4. 用户数据权利") {
            LegalList(listOf(
                "查询、更正、删除个人数据及注销账号",
                "随时在手机系统设置关闭 APP 各项设备权限"
            ))
        }
        LegalSection("5. 数据保存期限") {
            LegalList(listOf(
                "普通订单资料：行程结束后保存 24个月",
                "KYC 护照、人脸生物数据：账号存续期间保存，注销后30天内删除",
                "用户发送给司机的照片、视频、语音消息：行程结束保存 24 个月"
            ))
        }
    }
}

@Composable
private fun RefundPolicyContent() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        LegalSection("一、预订流程") {
            LegalList(listOf(
                "通过官网/App/客服渠道提交出行需求",
                "客服/管家确认可用车辆与报价，生成预订确认函",
                "用户确认预订并完成付款",
                "出行前 24 小时，司导将通过 WhatsApp/微信确认接载细节"
            ))
        }
        LegalSection("二、取消与退款规则") {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                RefundRule("出行前 48 小时及以上取消", "可申请退款订单基础金额全款。6% 支付通道服务费不予退还。")
                RefundRule("出行前 24–48 小时内取消", "退还订单基础金额 70%。6% 支付通道服务费不予退还。")
                RefundRule("出行前 24 小时以内取消 / 临时弃单", "不予退款。")
                RefundRule("用户个人原因无法过关、迟到、证件问题", "不予退款。")
                RefundRule("平台原因无法履约", "全额退款（包含用户支付的通道服务费）。")
            }
        }
        LegalSection("三、退款时效") {
            Text("审核通过后，退款将在 7–14 个工作日内原路退回。所有退款申请需通过官方客服渠道提交。",
                fontSize = 13.sp, lineHeight = 20.sp)
        }
        LegalHighlight("KYC 身份核验仅用于账号身份确认，核验成功不等于保证出行通关，出入境责任由用户本人承担。")
    }
}

@Composable
private fun PaymentFeeContent() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("所有订单结账时，额外收取订单总价 6% 的综合支付通道服务费，用于覆盖第三方支付网关手续费、跨境结算成本与通道运维成本。",
            fontSize = 14.sp, lineHeight = 22.sp, fontWeight = FontWeight.Medium)
        LegalList(listOf(
            "APP 页面展示的所有包车标价仅为车辆出行服务费，不包含支付渠道成本",
            "所有订单结账时将额外收取订单总价 6% 的综合支付通道服务费",
            "该费用用于：第三方支付网关手续费、跨境结算成本、通道运维成本",
            "6% 服务费一旦被第三方支付平台收取，按退款政策规则执行",
            "本 APP 不直接收取现金、不触碰资金流，所有交易由第三方合规支付网关处理"
        ))
    }
}

/* ===== 辅助组件 ===== */

@Composable
private fun LegalSection(title: String, content: @Composable ColumnScope.() -> Unit) {
    Column {
        Text(title, fontSize = 14.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 6.dp))
        content()
    }
}

@Composable
private fun LegalHighlight(text: String) {
    Surface(
        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.06f),
        shape = MaterialTheme.shapes.small,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text, fontSize = 12.sp, lineHeight = 18.sp, modifier = Modifier.padding(10.dp))
    }
}

@Composable
private fun LegalList(items: List<String>) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        items.forEach { item ->
            Row {
                Text("·  ", fontSize = 13.sp)
                Text(item, fontSize = 13.sp, lineHeight = 19.sp, modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun RefundRule(condition: String, policy: String) {
    Column(modifier = Modifier.padding(vertical = 2.dp)) {
        Text(condition, fontSize = 13.sp, fontWeight = FontWeight.Medium, lineHeight = 19.sp)
        Text(policy, fontSize = 12.sp, lineHeight = 18.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f))
    }
}
