package com.stellarelite.xingyuzhenlv.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stellarelite.xingyuzhenlv.i18n.t

// 密码规则：8-15位，至少1大写、1小写、1数字、1特殊符号
fun validatePassword(pw: String): Boolean {
    return pw.length in 8..15 &&
            pw.any { it.isUpperCase() } &&
            pw.any { it.isLowerCase() } &&
            pw.any { it.isDigit() } &&
            pw.any { "!@#$%^&*()_+-=[]{}|;:',.<>?/`~".contains(it) }
}

fun passwordHint(pw: String): String {
    val checks = listOf(
        "8-15字" to (pw.length in 8..15),
        "最少1大写" to pw.any { it.isUpperCase() },
        "最少1小写" to pw.any { it.isLowerCase() },
        "最少1数字" to pw.any { it.isDigit() },
        "最少1符号" to pw.any { "!@#$%^&*()_+-=[]{}|;:',.<>?/`~".contains(it) }
    )
    return "条件：" + checks.joinToString("  ") { "${if (it.second) "√" else "×"}${it.first}" }
}

@Composable
fun LoginEntryScreen(
    onLogin: () -> Unit = {},
    onRegister: () -> Unit = {},
    onBack: () -> Unit = {}
) {
    Box(
        modifier = Modifier.fillMaxSize().background(
            Brush.verticalGradient(listOf(Color(0xFF1A73E8), Color(0xFF0D47A1)))
        )
    ) {
        IconButton(onClick = onBack, modifier = Modifier.align(Alignment.TopStart).padding(16.dp)) {
            Icon(Icons.Filled.ArrowBack, "返回", tint = Color.White)
        }
        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("星域臻旅", fontSize = 36.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Spacer(Modifier.height(4.dp))
            Text("STELLARELITE", fontSize = 14.sp, color = Color.White.copy(alpha = 0.7f), letterSpacing = 4.sp)
            Spacer(Modifier.height(64.dp))
            Button(
                onClick = onLogin,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) { Text("登录", fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Color(0xFF1A73E8)) }
            Spacer(Modifier.height(16.dp))
            OutlinedButton(
                onClick = onRegister,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(14.dp),
                border = ButtonDefaults.outlinedButtonBorder.copy(brush = Brush.linearGradient(listOf(Color.White, Color.White)))
            ) { Text("注册", fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Color.White) }
            Spacer(Modifier.height(48.dp))
            TextButton(onClick = { }) { Text("跳过，先看看", color = Color.White.copy(alpha = 0.6f)) }
        }
    }
}

@Composable
fun RegisterScreen(onBack: () -> Unit = {}, onRegisterSuccess: () -> Unit = {}) {
    var email by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var agreeTerms by remember { mutableStateOf(false) }
    val pwValid = password.isNotEmpty() && validatePassword(password) && password == confirmPassword

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) { Icon(Icons.Filled.ArrowBack, "返回") }
            Spacer(Modifier.width(8.dp))
            Text("注册账号", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.height(24.dp))

        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("名称") }, modifier = Modifier.fillMaxWidth(), leadingIcon = { Icon(Icons.Filled.Person, null) })

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email), leadingIcon = { Icon(Icons.Filled.Email, null) })

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("密码") }, modifier = Modifier.fillMaxWidth(), visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password), leadingIcon = { Icon(Icons.Filled.Lock, null) }, trailingIcon = { IconButton(onClick = { showPassword = !showPassword }) { Icon(if (showPassword) Icons.Filled.VisibilityOff else Icons.Filled.Visibility, null) } })

        Spacer(Modifier.height(4.dp))
        Text(passwordHint(password), fontSize = 11.sp, color = if (password.isEmpty()) MaterialTheme.colorScheme.outline else if (validatePassword(password)) Color(0xFF4CAF50) else MaterialTheme.colorScheme.outline)

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(value = confirmPassword, onValueChange = { confirmPassword = it }, label = { Text("确认密码") }, modifier = Modifier.fillMaxWidth(), visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password), leadingIcon = { Icon(Icons.Filled.Lock, null) }, isError = confirmPassword.isNotEmpty() && password != confirmPassword)

        Spacer(Modifier.height(12.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = agreeTerms, onCheckedChange = { agreeTerms = it })
            Text("我已阅读并同意", fontSize = 13.sp)
            TextButton(onClick = { }, contentPadding = PaddingValues(4.dp)) { Text("服务条款", fontSize = 13.sp, color = MaterialTheme.colorScheme.primary) }
        }

        Spacer(Modifier.height(16.dp))

        Button(onClick = { onRegisterSuccess() }, modifier = Modifier.fillMaxWidth().height(52.dp), shape = RoundedCornerShape(14.dp), enabled = name.isNotBlank() && email.isNotBlank() && pwValid && agreeTerms) {
            Text("注册", fontSize = 16.sp, fontWeight = FontWeight.Medium)
        }

        Spacer(Modifier.height(12.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text("已有账号？", fontSize = 14.sp, color = MaterialTheme.colorScheme.outline)
            TextButton(onClick = onBack) { Text("立即登录", fontSize = 14.sp, fontWeight = FontWeight.Medium) }
        }
    }
}

@Composable
fun LoginScreen(
    onBack: () -> Unit = {},
    onLoginSuccess: () -> Unit = {},
    onForgotPassword: () -> Unit = {},
    onRegister: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var rememberMe by remember { mutableStateOf(true) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) { Icon(Icons.Filled.ArrowBack, "返回") }
            Spacer(Modifier.width(8.dp))
            Text("登录", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.height(32.dp))

        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email), leadingIcon = { Icon(Icons.Filled.Email, null) })

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("密码") }, modifier = Modifier.fillMaxWidth(), visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password), leadingIcon = { Icon(Icons.Filled.Lock, null) }, trailingIcon = { IconButton(onClick = { showPassword = !showPassword }) { Icon(if (showPassword) Icons.Filled.VisibilityOff else Icons.Filled.Visibility, null) } })

        Spacer(Modifier.height(8.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Row(verticalAlignment = Alignment.CenterVertically) { Checkbox(checked = rememberMe, onCheckedChange = { rememberMe = it }); Text("记住我", fontSize = 13.sp) }
            TextButton(onClick = onForgotPassword) { Text("忘记密码？", fontSize = 13.sp) }
        }

        Spacer(Modifier.height(24.dp))
        Button(onClick = { onLoginSuccess() }, modifier = Modifier.fillMaxWidth().height(52.dp), shape = RoundedCornerShape(14.dp), enabled = email.isNotBlank() && password.isNotBlank()) {
            Text("登录", fontSize = 16.sp, fontWeight = FontWeight.Medium)
        }

        Spacer(Modifier.height(12.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text("还没有账号？", fontSize = 14.sp, color = MaterialTheme.colorScheme.outline)
            TextButton(onClick = onRegister) { Text("立即注册", fontSize = 14.sp, fontWeight = FontWeight.Medium) }
        }
    }
}

@Composable
fun ForgotPasswordScreen(onBack: () -> Unit = {}, onResetSuccess: () -> Unit = {}) {
    var email by remember { mutableStateOf("") }
    var code by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var step by remember { mutableIntStateOf(1) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) { Icon(Icons.Filled.ArrowBack, "返回") }
            Spacer(Modifier.width(8.dp))
            Text("忘记密码", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.height(12.dp))
        Text(if (step == 1) "请输入注册时使用的Email，我们将发送验证码" else "请输入验证码并设置新密码", fontSize = 14.sp, color = MaterialTheme.colorScheme.outline)
        Spacer(Modifier.height(24.dp))

        if (step == 1) {
            OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email), leadingIcon = { Icon(Icons.Filled.Email, null) })
            Spacer(Modifier.height(24.dp))
            Button(onClick = { step = 2 }, modifier = Modifier.fillMaxWidth().height(52.dp), shape = RoundedCornerShape(14.dp), enabled = email.isNotBlank()) {
                Text("获取验证码", fontSize = 16.sp, fontWeight = FontWeight.Medium)
            }
        } else {
            OutlinedTextField(value = code, onValueChange = { if (it.length <= 6) code = it }, label = { Text("验证码") }, modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), leadingIcon = { Icon(Icons.Filled.Pin, null) })
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(value = newPassword, onValueChange = { newPassword = it }, label = { Text("新密码") }, modifier = Modifier.fillMaxWidth(), visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password), leadingIcon = { Icon(Icons.Filled.Lock, null) }, trailingIcon = { IconButton(onClick = { showPassword = !showPassword }) { Icon(if (showPassword) Icons.Filled.VisibilityOff else Icons.Filled.Visibility, null) } })
            Spacer(Modifier.height(4.dp))
            Text(passwordHint(newPassword), fontSize = 11.sp, color = if (newPassword.isEmpty()) MaterialTheme.colorScheme.outline else if (validatePassword(newPassword)) Color(0xFF4CAF50) else MaterialTheme.colorScheme.outline)
            Spacer(Modifier.height(24.dp))
            Button(onClick = { onResetSuccess() }, modifier = Modifier.fillMaxWidth().height(52.dp), shape = RoundedCornerShape(14.dp), enabled = code.isNotBlank() && validatePassword(newPassword)) {
                Text("重置密码", fontSize = 16.sp, fontWeight = FontWeight.Medium)
            }
            Spacer(Modifier.height(8.dp))
            TextButton(onClick = { }) { Text("重新发送验证码", fontSize = 13.sp) }
        }
    }
}
