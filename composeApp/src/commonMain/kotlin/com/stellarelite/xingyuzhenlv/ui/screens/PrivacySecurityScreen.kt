package com.stellarelite.xingyuzhenlv.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PrivacySecurityScreen(onBack: () -> Unit = {}, onKYC: () -> Unit = {}) {
    val scrollState = rememberScrollState()
    // KYC 状态: "pending"=未认证, "reviewing"=审核中, "done"=已完成
    var kycStatus by remember { mutableStateOf("pending") }
    var oldPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var showChangePassword by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(scrollState).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) { Icon(Icons.Filled.ArrowBack, "返回") }
            Spacer(Modifier.width(8.dp))
            Text("隐私与安全", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }

        // 身份信息卡片
        Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("身份信息", fontSize = 15.sp, fontWeight = FontWeight.SemiBold)

                when (kycStatus) {
                    "done" -> KYCDoneCard()
                    "reviewing" -> KYCStatusBanner("KYC 实名认证审核中，24小时内完成验证。", Color(0xFFFF9800))
                    else -> KYCStatusBanner("KYC 实名认证未认证，请到下方进行验证。", Color(0xFFFF5722))
                }
            }
        }

        // 更改密码
        Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))) {
            Column(Modifier.padding(16.dp)) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("更改密码", fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                    IconButton(onClick = { showChangePassword = !showChangePassword }) {
                        Icon(if (showChangePassword) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore, null)
                    }
                }

                if (showChangePassword) {
                    Spacer(Modifier.height(12.dp))
                    OutlinedTextField(
                        value = oldPassword,
                        onValueChange = { oldPassword = it },
                        label = { Text("原始密码") },
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                        leadingIcon = { Icon(Icons.Filled.Lock, null) }
                    )
                    Spacer(Modifier.height(12.dp))
                    OutlinedTextField(
                        value = newPassword,
                        onValueChange = { newPassword = it },
                        label = { Text("新密码") },
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                        leadingIcon = { Icon(Icons.Filled.Lock, null) }
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(passwordHint(newPassword), fontSize = 11.sp, color = if (newPassword.isEmpty()) MaterialTheme.colorScheme.outline else if (validatePassword(newPassword)) Color(0xFF4CAF50) else MaterialTheme.colorScheme.outline)
                    Spacer(Modifier.height(12.dp))
                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        label = { Text("确认密码") },
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                        leadingIcon = { Icon(Icons.Filled.Lock, null) }
                    )
                    Spacer(Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = showPassword, onCheckedChange = { showPassword = it })
                        Text("显示密码", fontSize = 13.sp)
                    }
                    Spacer(Modifier.height(12.dp))
                    Button(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        enabled = oldPassword.isNotBlank() && validatePassword(newPassword) && newPassword == confirmPassword
                    ) {
                        Text("确认更改", fontSize = 15.sp, fontWeight = FontWeight.Medium)
                    }
                }
            }
        }

        // KYC 实名认证入口
        Card(onClick = onKYC, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))) {
            Column(Modifier.padding(16.dp)) {
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.VerifiedUser, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(28.dp))
                    Spacer(Modifier.width(12.dp))
                    Column(Modifier.weight(1f)) {
                        Text("KYC 实名认证", fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                        Text("上传证件完成身份核验，解锁全部服务", fontSize = 12.sp, color = MaterialTheme.colorScheme.outline)
                    }
                    Icon(Icons.Filled.ChevronRight, null, tint = MaterialTheme.colorScheme.outline)
                }
            }
        }

        // 保存按钮
        Button(onClick = { }, modifier = Modifier.fillMaxWidth().height(52.dp), shape = RoundedCornerShape(14.dp)) {
            Text("保存", fontSize = 16.sp, fontWeight = FontWeight.Medium)
        }

        Spacer(Modifier.height(80.dp))
    }
}

@Composable
private fun KYCStatusBanner(message: String, color: Color) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        color = color.copy(alpha = 0.1f)
    ) {
        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Filled.Info, null, tint = color, modifier = Modifier.size(18.dp))
            Spacer(Modifier.width(8.dp))
            Text(message, fontSize = 13.sp, color = color, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
private fun KYCDoneCard() {
    Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f))) {
        Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.CheckCircle, null, tint = Color(0xFF4CAF50), modifier = Modifier.size(16.dp))
                Spacer(Modifier.width(6.dp))
                Text("KYC 已验证通过", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF4CAF50))
            }
            Text("护照信息已绑定，可使用全部服务", fontSize = 12.sp, color = MaterialTheme.colorScheme.outline)
        }
    }
}
