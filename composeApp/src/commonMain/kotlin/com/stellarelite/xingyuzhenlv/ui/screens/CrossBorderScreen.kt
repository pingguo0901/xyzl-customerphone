package com.stellarelite.xingyuzhenlv.ui.screens

import androidx.compose.foundation.clickable
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
import com.stellarelite.xingyuzhenlv.i18n.t

@Composable
fun CrossBorderScreen(onBack: () -> Unit = {}) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(scrollState).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(Icons.Filled.ArrowBack, "返回")
            }
            Spacer(Modifier.width(8.dp))
            Text("跨境手册", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }

        // ===== 新加坡 =====
        CountryCrossBorderCard(
            flag = "🇸🇬",
            countryName = "新加坡",
            items = listOf(
                CrossBorderItem("入境卡", "SG Arrival Card", "https://eservices.ica.gov.sg/sgarrivalcard/fvipa"),
                CrossBorderItem("入境申报", "Customs Declaration", "https://m.customs.gov.sg/CustomsTravellerPortal/Personal-Information"),
                CrossBorderItem("跨境手册", "Cross-border Guide", "#")
            )
        )

        // ===== 马来西亚 =====
        CountryCrossBorderCard(
            flag = "🇲🇾",
            countryName = "马来西亚",
            items = listOf(
                CrossBorderItem("入境卡", "MDAC", "https://imigresen-online.imi.gov.my/mdac/main?registerMain"),
                CrossBorderItem("入境申报", "Customs Declaration", "https://www.customs.gov.my"),
                CrossBorderItem("跨境手册", "Cross-border Guide", "#")
            )
        )

        Spacer(Modifier.height(80.dp))
    }
}

data class CrossBorderItem(
    val title: String,
    val subtitle: String,
    val url: String
)

@Composable
private fun CountryCrossBorderCard(
    flag: String,
    countryName: String,
    items: List<CrossBorderItem>
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(flag, fontSize = 28.sp)
                Spacer(Modifier.width(12.dp))
                Text(countryName, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.height(16.dp))

            items.forEach { item ->
                CrossBorderItemRow(item)
                if (item != items.last()) {
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 4.dp),
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                    )
                }
            }
        }
    }
}

@Composable
private fun CrossBorderItemRow(item: CrossBorderItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* TODO: 打开链接 item.url */ }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val icon = when {
            item.title.contains("入境卡") -> Icons.Filled.Description
            item.title.contains("申报") -> Icons.Filled.Assignment
            else -> Icons.Filled.MenuBook
        }
        val iconColor = when {
            item.title.contains("入境卡") -> Color(0xFF4CAF50)
            item.title.contains("申报") -> Color(0xFFFF9800)
            else -> Color(0xFF2196F3)
        }

        Icon(icon, null, tint = iconColor, modifier = Modifier.size(28.dp))

        Spacer(Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(item.title, fontSize = 15.sp, fontWeight = FontWeight.Medium)
            Text(item.subtitle, fontSize = 12.sp, color = MaterialTheme.colorScheme.outline)
        }

        Icon(
            Icons.Filled.ChevronRight,
            null,
            tint = MaterialTheme.colorScheme.outline,
            modifier = Modifier.size(20.dp)
        )
    }
}
