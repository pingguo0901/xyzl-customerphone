package com.stellarelite.xingyuzhenlv.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class Currency(val code: String, val symbol: String, val displayName: String) {
    MYR("MYR", "RM", "马来西亚林吉特"),
    SGD("SGD", "S$", "新加坡元"),
    CNY("CNY", "¥", "人民币"),
    USD("USD", "$", "美元"),
    EUR("EUR", "€", "欧元"),
    USDT("USDT", "₮", "泰达币")
}

object CurrencyManager {
    var current = mutableStateOf(Currency.SGD)
}

@Composable
fun CurrencyScreen(onBack: () -> Unit = {}) {
    val currentCurrency by remember { CurrencyManager.current }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) { Icon(Icons.Filled.ArrowBack, "返回") }
            Spacer(Modifier.width(8.dp))
            Text("货币", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(8.dp))
        Text("选择默认显示货币", fontSize = 14.sp, color = MaterialTheme.colorScheme.outline)

        Spacer(Modifier.height(20.dp))

        Currency.entries.forEach { currency ->
            val selected = currentCurrency == currency

            Row(
                modifier = Modifier.fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .then(if (selected) Modifier.background(MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)) else Modifier)
                    .clickable { CurrencyManager.current = currency }
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(currency.symbol, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                Spacer(Modifier.width(14.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(currency.displayName, fontSize = 16.sp, fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal)
                    Text(currency.code, fontSize = 12.sp, color = MaterialTheme.colorScheme.outline)
                }
                if (selected) {
                    Icon(Icons.Filled.Check, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                }
            }

            if (currency != Currency.entries.last()) {
                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))
            }
        }
    }
}
