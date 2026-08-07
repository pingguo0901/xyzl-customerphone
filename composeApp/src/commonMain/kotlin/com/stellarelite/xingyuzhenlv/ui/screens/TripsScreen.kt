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
import com.stellarelite.xingyuzhenlv.i18n.t

enum class TripTab { ALL, BOOKED, HISTORY, CANCELLED }

data class TripRecord(
    val id: String,
    val status: String,
    val statusColor: Color,
    val date: String,
    val time: String,
    val origin: String,
    val destination: String,
    val price: String,
    val tab: TripTab
)

@Composable
fun TripsScreen() {
    var selectedTab by remember { mutableStateOf(TripTab.ALL) }
    val scrollState = rememberScrollState()

    val trips = remember {
        listOf(
            TripRecord("T001", "司机已出发", Color(0xFF4CAF50), "2026-08-08", "14:30", "吉隆坡双威酒店", "KLIA 吉隆坡国际机场", "RM 168", TripTab.BOOKED),
            TripRecord("T002", "已确认", Color(0xFF2196F3), "2026-08-10", "09:00", "新山关卡", "新加坡乌节路", "RM 250", TripTab.BOOKED),
            TripRecord("T003", "已完成", Color(0xFF9E9E9E), "2026-08-01", "11:00", "槟城机场", "乔治市酒店", "RM 80", TripTab.HISTORY),
            TripRecord("T004", "已完成", Color(0xFF9E9E9E), "2026-07-28", "16:30", "KL Sentral", "云顶高原", "RM 200", TripTab.HISTORY),
            TripRecord("T005", "已完成", Color(0xFF9E9E9E), "2026-07-20", "08:00", "马六甲市中心", "KLIA2", "RM 150", TripTab.HISTORY),
            TripRecord("T006", "已取消", Color(0xFFF44336), "2026-07-15", "12:00", "新山 KSL", "新加坡樟宜机场", "RM 220", TripTab.CANCELLED),
            TripRecord("T007", "已取消", Color(0xFFF44336), "2026-07-10", "06:00", "吉隆坡市中心", "槟城", "RM 350", TripTab.CANCELLED),
        )
    }

    val filtered = when (selectedTab) {
        TripTab.ALL -> trips
        TripTab.BOOKED -> trips.filter { it.tab == TripTab.BOOKED }
        TripTab.HISTORY -> trips.filter { it.tab == TripTab.HISTORY }
        TripTab.CANCELLED -> trips.filter { it.tab == TripTab.CANCELLED }
    }

    Column(modifier = Modifier.fillMaxSize().padding(top = 8.dp)) {
        Text(t("tab_trips"), fontSize = 22.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 16.dp))

        Spacer(Modifier.height(8.dp))

        // 标签切换
        TabRow(
            selectedTabIndex = selectedTab.ordinal,
            containerColor = MaterialTheme.colorScheme.background,
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            listOf("全部", "已预约", "历史行程", "已取消").forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab.ordinal == index,
                    onClick = { selectedTab = TripTab.entries[index] },
                    text = {
                        Text(title, fontWeight = if (selectedTab.ordinal == index) FontWeight.Bold else FontWeight.Normal,
                            color = if (selectedTab.ordinal == index) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline)
                    }
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        if (filtered.isEmpty()) {
            Box(modifier = Modifier.fillMaxWidth().padding(40.dp), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Filled.AirportShuttle, null, tint = MaterialTheme.colorScheme.outline, modifier = Modifier.size(48.dp))
                    Spacer(Modifier.height(8.dp))
                    Text("暂无行程", color = MaterialTheme.colorScheme.outline)
                }
            }
        }

        Column(modifier = Modifier.verticalScroll(scrollState).padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            filtered.forEach { trip -> TripCard(trip) }
            Spacer(Modifier.height(80.dp))
        }
    }
}

@Composable
private fun TripCard(trip: TripRecord) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // 状态 + 日期时间
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Schedule, null, tint = MaterialTheme.colorScheme.outline, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(4.dp))
                    Text(trip.date, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface)
                    Spacer(Modifier.width(6.dp))
                    Text(trip.time, fontSize = 13.sp, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onSurface)
                }
                Surface(shape = RoundedCornerShape(12.dp), color = trip.statusColor.copy(alpha = 0.12f)) {
                    Text(trip.status, Modifier.padding(horizontal = 10.dp, vertical = 3.dp),
                        fontSize = 12.sp, color = trip.statusColor, fontWeight = FontWeight.Medium)
                }
            }

            Spacer(Modifier.height(10.dp))

            // 起点
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.size(8.dp).background(Color(0xFF4CAF50), CircleShape))
                Spacer(Modifier.width(8.dp))
                Text(trip.origin, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.weight(1f))
            }

            Spacer(Modifier.height(4.dp))
            Box(Modifier.width(1.dp).height(16.dp).padding(start = 3.dp).background(MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)))

            // 目的地
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.size(8.dp).background(Color(0xFFF44336), CircleShape))
                Spacer(Modifier.width(8.dp))
                Text(trip.destination, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.weight(1f))
            }

            Spacer(Modifier.height(10.dp))

            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))

            Spacer(Modifier.height(8.dp))

            // 价格 + 查看详情
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(trip.price, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                OutlinedButton(
                    onClick = { },
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp)
                ) {
                    Text("查看详情", fontSize = 13.sp, fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}
