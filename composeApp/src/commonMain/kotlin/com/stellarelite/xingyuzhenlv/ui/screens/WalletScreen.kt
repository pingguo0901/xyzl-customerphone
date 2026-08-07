package com.stellarelite.xingyuzhenlv.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class WalletTransaction(
    val id: String,
    val type: String,
    val amount: String,
    val isIncome: Boolean,
    val description: String,
    val time: String,
    val myrAmount: Double = 0.0
)

@Composable
fun WalletScreen(onBack: () -> Unit = {}) {
    val scrollState = rememberScrollState()
    var showTopUp by remember { mutableStateOf(false) }
    var topUpAmount by remember { mutableStateOf("") }
    val showBalance = CurrencyManager.formatPrice(1280.5)

    val transactions = remember {
        listOf(
            WalletTransaction("W001", "消费", "", false, "行程支付 KL→KLIA", "2026-08-07 14:30", 168.0),
            WalletTransaction("W002", "充值", "", true, "银行卡充值", "2026-08-06 10:15", 500.0),
            WalletTransaction("W003", "消费", "", false, "行程支付 新山→新加坡", "2026-08-05 09:00", 250.0),
            WalletTransaction("W004", "退款", "", true, "行程取消退款", "2026-08-03 16:00", 80.0),
            WalletTransaction("W005", "消费", "", false, "行程支付 KL→云顶", "2026-07-28 11:30", 200.0),
            WalletTransaction("W006", "充值", "", true, "支付宝充值", "2026-07-25 08:00", 1000.0),
        )
    }

    Column(modifier = Modifier.fillMaxSize().verticalScroll(scrollState).padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) { Icon(Icons.Filled.ArrowBack, "返回") }
            Spacer(Modifier.width(8.dp))
            Text("我的钱包", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(20.dp))

        // 余额卡片
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Column(Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("当前余额", fontSize = 13.sp, color = Color.White.copy(alpha = 0.7f))
                Spacer(Modifier.height(8.dp))
                Text(showBalance, fontSize = 36.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Spacer(Modifier.height(16.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Text("MYR · 星域臻旅钱包", fontSize = 12.sp, color = Color.White.copy(alpha = 0.6f))
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // 充值按钮
        Button(
            onClick = { showTopUp = true },
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
        ) {
            Icon(Icons.Filled.AddCircle, null, modifier = Modifier.size(20.dp))
            Spacer(Modifier.width(8.dp))
            Text("充值", fontSize = 16.sp, fontWeight = FontWeight.Medium)
        }

        Spacer(Modifier.height(24.dp))

        // 明细记录
        Text("明细记录", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(12.dp))

        transactions.forEach { tx ->
            TransactionItem(tx)
            Spacer(Modifier.height(8.dp))
        }

        Spacer(Modifier.height(80.dp))
    }

    // 充值弹窗
    if (showTopUp) {
        AlertDialog(
            onDismissRequest = { showTopUp = false },
            title = { Text("充值", fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    Text("请输入充值金额", fontSize = 14.sp, color = MaterialTheme.colorScheme.outline)
                    Spacer(Modifier.height(12.dp))
                    OutlinedTextField(
                        value = topUpAmount,
                        onValueChange = { topUpAmount = it },
                        label = { Text("金额 (MYR)") },
                        modifier = Modifier.fillMaxWidth(),
                        prefix = { Text("RM ") }
                    )
                    Spacer(Modifier.height(12.dp))
                    Text("充值后将自动存入钱包余额", fontSize = 12.sp, color = MaterialTheme.colorScheme.outline)
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    showTopUp = false
                    topUpAmount = ""
                }) { Text("确认充值") }
            },
            dismissButton = {
                TextButton(onClick = { showTopUp = false }) { Text("取消") }
            }
        )
    }
}

@Composable
private fun TransactionItem(tx: WalletTransaction) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val iconColor = if (tx.isIncome) Color(0xFF4CAF50) else Color(0xFFF44336)
            val icon = if (tx.isIncome) Icons.Filled.ArrowDownward else Icons.Filled.ArrowUpward

            Box(
                modifier = Modifier.size(40.dp).background(iconColor.copy(alpha = 0.1f), RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, null, tint = iconColor, modifier = Modifier.size(20.dp))
            }

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(tx.description, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                Text("${tx.type} · ${tx.time}", fontSize = 11.sp, color = MaterialTheme.colorScheme.outline)
            }

            Text(
                CurrencyManager.formatPrice(tx.myrAmount),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = if (tx.isIncome) Color(0xFF4CAF50) else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
