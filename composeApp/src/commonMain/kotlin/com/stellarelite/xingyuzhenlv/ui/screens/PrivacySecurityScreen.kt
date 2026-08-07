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
fun PrivacySecurityScreen(onBack: () -> Unit = {}) {
    val scrollState = rememberScrollState()
    var facePhotoSelected by remember { mutableStateOf(false) }
    var fullName by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var birthCountry by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var passportPhotoSelected by remember { mutableStateOf(false) }
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

        // 身份验证资料
        Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("身份验证资料", fontSize = 15.sp, fontWeight = FontWeight.SemiBold)

                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    PhotoPickerButton("人脸照", Icons.Filled.Face, facePhotoSelected, Modifier.weight(1f))
                    PhotoPickerButton("护照照片", Icons.Filled.Book, passportPhotoSelected, Modifier.weight(1f))
                }
                OutlinedTextField(value = fullName, onValueChange = { fullName = it }, label = { Text("姓名") }, modifier = Modifier.fillMaxWidth())
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(value = birthDate, onValueChange = { birthDate = it }, label = { Text("出生日期") }, modifier = Modifier.weight(1f), leadingIcon = { Icon(Icons.Filled.CalendarMonth, null) })
                    OutlinedTextField(value = birthCountry, onValueChange = { birthCountry = it }, label = { Text("出生国家") }, modifier = Modifier.weight(1f))
                }
                OutlinedTextField(value = address, onValueChange = { address = it }, label = { Text("住址") }, modifier = Modifier.fillMaxWidth())
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

        // 保存按钮
        Button(onClick = { }, modifier = Modifier.fillMaxWidth().height(52.dp), shape = RoundedCornerShape(14.dp)) {
            Text("保存", fontSize = 16.sp, fontWeight = FontWeight.Medium)
        }

        Spacer(Modifier.height(80.dp))
    }
}

@Composable
private fun PhotoPickerButton(label: String, icon: androidx.compose.ui.graphics.vector.ImageVector, selected: Boolean, modifier: Modifier = Modifier) {
    var showOptions by remember { mutableStateOf(false) }

    OutlinedButton(
        onClick = { showOptions = true },
        modifier = modifier.height(56.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = if (selected) Color(0xFF4CAF50) else MaterialTheme.colorScheme.outline
        )
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                if (selected) Icons.Filled.CheckCircle else icon,
                null,
                tint = if (selected) Color(0xFF4CAF50) else MaterialTheme.colorScheme.outline,
                modifier = Modifier.size(20.dp)
            )
            Spacer(Modifier.height(2.dp))
            Text(
                if (selected) "已选择" else label,
                fontSize = 11.sp
            )
        }
    }

    if (showOptions) {
        AlertDialog(
            onDismissRequest = { showOptions = false },
            title = { Text(label, fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    TextButton(onClick = { /* TODO: 打开相机拍照 */; showOptions = false }) {
                        Icon(Icons.Filled.CameraAlt, null, modifier = Modifier.size(20.dp))
                        Spacer(Modifier.width(10.dp))
                        Text("拍照", fontSize = 15.sp)
                    }
                    TextButton(onClick = { /* TODO: 从相册选择 */; showOptions = false }) {
                        Icon(Icons.Filled.PhotoLibrary, null, modifier = Modifier.size(20.dp))
                        Spacer(Modifier.width(10.dp))
                        Text("从相册选择", fontSize = 15.sp)
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { showOptions = false }) { Text("取消") }
            }
        )
    }
}
