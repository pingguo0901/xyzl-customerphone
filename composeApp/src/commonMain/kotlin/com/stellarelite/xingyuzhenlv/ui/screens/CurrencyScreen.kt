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

import com.stellarelite.xingyuzhenlv.i18n.t

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
    
    fun formatPrice(myrAmount: Double): String {
        val rates = mapOf(
            Currency.MYR to 1.0,
            Currency.SGD to 0.30,
            Currency.CNY to 1.55,
            Currency.USD to 0.22,
            Currency.EUR to 0.20,
            Currency.USDT to 0.22
        )
        val converted = myrAmount * (rates[current.value] ?: 1.0)
        return "${current.value.symbol} %.2f".format(converted)
    }
}

@Composable
fun CurrencyScreen(onBack: () -> Unit = {}) {
    val currentCurrency by remember { CurrencyManager.current }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) { Icon(Icons.Filled.ArrowBack, "返回") }
            Spacer(Modifier.width(8.dp))
            Text(t("currency_title"), fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(8.dp))
        Text(t("currency_hint"), fontSize = 14.sp, color = MaterialTheme.colorScheme.outline)

        Spacer(Modifier.height(20.dp))

        Currency.entries.forEach { currency ->
            val selected = currentCurrency == currency

            Row(
                modifier = Modifier.fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .then(if (selected) Modifier.background(MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)) else Modifier)
                    .clickable { CurrencyManager.current.value = currency }
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
