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

@Composable fun PrivacyPolicyScreen(onBack: () -> Unit = {}) {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) { Icon(Icons.Filled.ArrowBack, contentDescription = "返回") }
            Spacer(Modifier.width(8.dp))
            Text("隐私政策", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }

        HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Text(
                "PRIVACY POLICY（马来西亚 PDPA 2010）",
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
                "本隐私政策依据马来西亚《个人数据保护法 PDPA 2010（2024修订案）》制定。用户下载、打开、使用本 APP 即视为完全阅读、理解并同意本隐私政策。",
                fontSize = 15.sp,
                lineHeight = 24.sp,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(Modifier.height(16.dp))

            HighlightBox("特别说明：人脸生物识别数据属于敏感个人数据，提交 KYC 即代表你明确、明示同意我们处理你的护照资料及人脸生物信息。不完成身份核验，将无法提交包车订单。")

            Spacer(Modifier.height(20.dp))

            // 1. 收集的个人信息
            NumberedSection("1. 收集的个人信息") {
                Text(
                    "本 APP 仅收集包车出行、反诈核验、出行沟通必要信息，不收集任何无关隐私数据：",
                    fontSize = 14.sp,
                    lineHeight = 22.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f)
                )
                Spacer(Modifier.height(8.dp))
                NumberedList(listOf(
                    "用户姓名、手机号码、电子邮箱、微信号",
                    "出行信息：接送地点、出行日期、时间、乘车人数",
                    "KYC 身份核验资料（下单强制）：护照照片、护照 MRZ 信息、人脸活体自拍（生物识别数据），用于账号实人核验、防范诈骗、跨境出行身份核对",
                    "跨境通关所需：乘客护照资料",
                    "设备权限相关数据：位置信息（接驾定位）、相机（KYC证件拍摄）、相册（用户手动选择上传）、麦克风（语音消息/通话）、通讯录（用户主动选择导入联系人）、通知推送",
                    "基础设备日志：设备型号、系统版本、崩溃日志"
                ))
            }

            HighlightBox("重要声明：信用卡、电子钱包、支付密码等支付敏感信息，本平台全程不收集、不保存、不获取，全部由第三方支付网关独立处理。")

            Spacer(Modifier.height(20.dp))

            // 2. 信息使用目的
            NumberedSection("2. 信息使用目的") {
                NumberedList(listOf(
                    "完成 KYC 实人身份核验，防范账号冒用、订单诈骗",
                    "确认、安排、执行用户的新马跨境包车行程",
                    "获取位置、接收用户上传的照片视频、语音消息，方便司机识别接驾地点",
                    "推送订单变更、行程提醒通知",
                    "满足新马两国边境通关报备要求",
                    "处理用户咨询、售后、退款、投诉",
                    "防范订单欺诈，保障出行安全"
                ))
                Spacer(Modifier.height(8.dp))
                Text(
                    "人脸生物数据仅用于一次身份比对核验，不会用于其他无关用途。相册、通讯录仅在用户手动主动选择文件/联系人时才读取。",
                    fontSize = 14.sp,
                    lineHeight = 22.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f)
                )
            }

            Spacer(Modifier.height(20.dp))

            // 3. 信息共享范围
            NumberedSection("3. 信息共享范围") {
                Text("仅在必要场景合规共享：", fontSize = 14.sp, lineHeight = 22.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f))
                Spacer(Modifier.height(8.dp))
                NumberedList(listOf(
                    "合作车辆司机：仅获取完成行程所需最低信息（接送位置、用户发送的照片视频语音）；不会传递人脸生物数据、完整通讯录",
                    "马来西亚、新加坡海关依法所需通关资料",
                    "第三方支付网关（仅支付流程必要数据）"
                ))
                Spacer(Modifier.height(8.dp))
                Text(
                    "本平台绝不会出售、出租、商用用户个人资料、护照、人脸生物识别数据。",
                    fontSize = 14.sp,
                    lineHeight = 22.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f),
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(Modifier.height(20.dp))

            // 4. 用户数据权利
            NumberedSection("4. 用户数据权利（PDPA 法定权利）") {
                NumberedList(listOf(
                    "查询我方保存的个人资料",
                    "申请更正错误资料",
                    "申请删除个人数据、护照文件与人脸生物记录，注销账号（撤回生物识别同意后，账号将无法继续下单包车服务）",
                    "用户可随时在手机系统设置关闭 APP 各项设备权限；关闭位置、相机、麦克风、相册权限会影响接驾沟通、KYC 核验功能"
                ))
                Spacer(Modifier.height(8.dp))
                Text(
                    "可通过官方 WhatsApp 或邮箱提交申请。",
                    fontSize = 14.sp,
                    lineHeight = 22.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f)
                )
            }

            Spacer(Modifier.height(20.dp))

            // 5. 数据保存期限
            NumberedSection("5. 数据保存期限") {
                NumberedList(listOf(
                    "普通订单资料：行程结束后保存 24个月，用于纠纷、售后、核查使用，到期统一清理删除",
                    "KYC 护照、人脸生物识别数据：账号存续期间保存；用户注销账号后，30天内全部彻底删除人脸生物信息；仅保留脱敏订单记录用于纠纷备查",
                    "用户发送给司机的照片、视频、语音消息：行程结束保存 24 个月，到期清除"
                ))
            }

            Spacer(Modifier.height(20.dp))

            // 6. 未成年人条款
            NumberedSection("6. 未成年人条款") {
                Text(
                    "本服务仅限 18 岁以上用户预订。未成年人乘车，必须由成年监护人完成 KYC 实名认证下单与陪同。",
                    fontSize = 14.sp,
                    lineHeight = 22.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f)
                )
            }

            Spacer(Modifier.height(20.dp))

            // 7. Cookie与访问日志
            NumberedSection("7. Cookie 与访问日志") {
                Text(
                    "用户访问本平台政策网页时，系统会自动记录基础访问日志用于故障排查与安全维护。本 APP 不植入追踪程序、不采集隐私行为数据。",
                    fontSize = 14.sp,
                    lineHeight = 22.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f)
                )
            }

            Spacer(Modifier.height(20.dp))

            // 8. 政策更新
            NumberedSection("8. 政策更新") {
                Text(
                    "本隐私政策可随时更新，更新后网页即时生效，用户持续使用 APP 即默认接受最新条款。",
                    fontSize = 14.sp,
                    lineHeight = 22.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f)
                )
            }

            Spacer(Modifier.height(32.dp))

            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))
            Spacer(Modifier.height(16.dp))
            Text("国际官网对应页面：", fontSize = 12.sp, color = MaterialTheme.colorScheme.outline)
            Text(
                "www.stellarelite-xingyuzhenlv.com/#legal/privacy",
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
                "cn.stellarelite-xingyuzhenlv.com/#legal/privacy",
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Medium
            )

            Spacer(Modifier.height(32.dp))
        }
    }
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
private fun NumberedSection(title: String, sectionContent: @Composable () -> Unit) {
    Text(
        title,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.padding(bottom = 10.dp)
    )
    sectionContent()
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
