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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class TripDetail(
    val id: String,
    val status: String, val statusColor: Color,
    val date: String, val time: String,
    val origin: String, val destination: String,
    val adults: Int = 1, val children: Int = 0, val luggage: Int = 0,
    val vehicleType: String = "", val vehicleCount: Int = 1,
    val tripType: String = "", val notes: String = "",
    val whatsapp: String = "", val wechat: String = "",
    val driverName: String = "", val driverPhone: String = "", val licensePlate: String = "",
    val conciergeName: String = "", val conciergePhone: String = "",
    val price: String = ""
)

@Composable
fun TripDetailScreen(trip: TripDetail, onBack: () -> Unit = {}) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(scrollState).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(Icons.Filled.ArrowBack, "返回")
            }
            Spacer(Modifier.width(8.dp))
            Column {
                Text("行程详情", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Surface(shape = RoundedCornerShape(12.dp), color = trip.statusColor.copy(alpha = 0.12f)) {
                    Text(trip.status, Modifier.padding(horizontal = 10.dp, vertical = 3.dp),
                        fontSize = 12.sp, color = trip.statusColor, fontWeight = FontWeight.Medium)
                }
            }
        }

        // 预约时间
        DetailCard("📅 预约时间") {
            DetailRow("日期", trip.date)
            DetailRow("时间", trip.time)
        }

        // 行程详情
        DetailCard("📍 行程详情") {
            DetailRow("起点", trip.origin)
            DetailRow("目的地", trip.destination)
        }

        // 联系信息
        if (trip.whatsapp.isNotBlank() || trip.wechat.isNotBlank()) {
            DetailCard("📞 联系信息") {
                if (trip.whatsapp.isNotBlank()) DetailRow("WhatsApp", trip.whatsapp)
                if (trip.wechat.isNotBlank()) DetailRow("WeChat", trip.wechat)
            }
        }

        // 人数与行李
        DetailCard("👥 人数与行李") {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                CountBlock("大人", trip.adults)
                CountBlock("小孩", trip.children)
                CountBlock("行李", trip.luggage)
            }
        }

        // 类型车辆
        DetailCard("🚗 类型车辆") {
            DetailRow("车辆类型", trip.vehicleType)
            DetailRow("数量", "${trip.vehicleCount} 辆")
        }

        // 行程类型
        DetailCard("🛣️ 行程类型") {
            DetailRow("类型", trip.tripType)
        }

        // 备注
        if (trip.notes.isNotBlank()) {
            DetailCard("📝 备注") {
                Text(trip.notes, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface)
            }
        }

        // 负责司机
        if (trip.driverName.isNotBlank()) {
            DetailCard("👨‍✈️ 负责司机") {
                DetailRow("姓名", trip.driverName)
                if (trip.driverPhone.isNotBlank()) DetailRow("电话", trip.driverPhone)
                if (trip.licensePlate.isNotBlank()) DetailRow("车牌", trip.licensePlate)
            }
        }

        // 负责管家
        if (trip.conciergeName.isNotBlank()) {
            DetailCard("🤵 负责管家") {
                DetailRow("姓名", trip.conciergeName)
                if (trip.conciergePhone.isNotBlank()) DetailRow("电话", trip.conciergePhone)
            }
        }

        // 价格
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.8f))
        ) {
            Column(Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("💰 价格", fontSize = 14.sp, color = MaterialTheme.colorScheme.primary)
                Spacer(Modifier.height(8.dp))
                Text(trip.price, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            }
        }

        Spacer(Modifier.height(80.dp))
    }
}

@Composable
private fun DetailCard(title: String, content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
    ) {
        Column(Modifier.padding(14.dp)) {
            Text(title, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(10.dp))
            content()
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Row(
        Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, fontSize = 13.sp, color = MaterialTheme.colorScheme.outline)
        Text(value, fontSize = 13.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
private fun CountBlock(label: String, count: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(count.toString(), fontSize = 22.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
        Text(label, fontSize = 12.sp, color = MaterialTheme.colorScheme.outline)
    }
}
