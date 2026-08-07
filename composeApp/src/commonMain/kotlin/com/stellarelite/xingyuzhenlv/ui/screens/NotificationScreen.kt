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

data class NotificationItem(
    val id: String,
    val type: NotificationType,
    val title: String,
    val message: String,
    val time: String,
    val read: Boolean = false
)

enum class NotificationType(val label: String) {
    OFFICIAL("官方通知"), DRIVER("司机消息"), CONCIERGE("管家消息")
}

@Composable
fun NotificationScreen(onBack: () -> Unit = {}) {
    val scrollState = rememberScrollState()
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("全部", "官方通知", "司机消息", "管家消息")

    val notifications = remember {
        listOf(
            NotificationItem("1", NotificationType.OFFICIAL, "系统维护公告", "星域臻旅将于8月10日凌晨2:00-4:00进行系统维护，期间预约功能暂不可用。", "2026-08-07 10:00"),
            NotificationItem("2", NotificationType.CONCIERGE, "行程确认通知", "您预订的8月8日吉隆坡→机场行程已确认，司机陈师傅（WVF 8888）将准时接您。", "2026-08-07 09:30"),
            NotificationItem("3", NotificationType.DRIVER, "司机已出发", "陈师傅已在路上，预计15分钟后到达您指定的上车地点。", "2026-08-07 09:15"),
            NotificationItem("4", NotificationType.OFFICIAL, "新功能上线", "预约行程功能全新升级，支持多地址、包车时长、在线估价！", "2026-08-06 14:00"),
            NotificationItem("5", NotificationType.CONCIERGE, "跨境提醒", "您的新加坡跨境行程将于明天出发，请提前填写SG Arrival Card。", "2026-08-06 11:00"),
        )
    }

    val filtered = if (selectedTab == 0) notifications
        else notifications.filter { it.type.ordinal == selectedTab - 1 }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(Icons.Filled.ArrowBack, "返回")
            }
            Spacer(Modifier.width(8.dp))
            Text("通知", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(12.dp))

        // 标签切换
        ScrollableTabRow(
            selectedTabIndex = selectedTab,
            edgePadding = 0.dp,
            divider = {},
            containerColor = MaterialTheme.colorScheme.background
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = {
                        Text(
                            title,
                            fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal,
                            color = if (selectedTab == index) MaterialTheme.colorScheme.primary
                                   else MaterialTheme.colorScheme.outline
                        )
                    }
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        // 通知列表
        Column(
            modifier = Modifier.verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (filtered.isEmpty()) {
                Box(modifier = Modifier.fillMaxWidth().padding(40.dp), contentAlignment = Alignment.Center) {
                    Text("暂无通知", color = MaterialTheme.colorScheme.outline)
                }
            }
            filtered.forEach { notification ->
                NotificationCard(notification)
            }
        }
    }
}

@Composable
private fun NotificationCard(item: NotificationItem) {
    val typeColor = when (item.type) {
        NotificationType.OFFICIAL -> Color(0xFF1A73E8)
        NotificationType.DRIVER -> Color(0xFF4CAF50)
        NotificationType.CONCIERGE -> Color(0xFFFF9800)
    }
    val typeIcon = when (item.type) {
        NotificationType.OFFICIAL -> Icons.Filled.Campaign
        NotificationType.DRIVER -> Icons.Filled.DirectionsCar
        NotificationType.CONCIERGE -> Icons.Filled.SupportAgent
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (item.read) MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                             else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)
        )
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(typeIcon, null, tint = typeColor, modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(8.dp))
                Text(item.type.label, fontSize = 12.sp, color = typeColor, fontWeight = FontWeight.Medium)
                Spacer(Modifier.weight(1f))
                Text(item.time, fontSize = 11.sp, color = MaterialTheme.colorScheme.outline)
            }
            Spacer(Modifier.height(6.dp))
            Text(item.title, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(4.dp))
            Text(item.message, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f), lineHeight = 18.sp)
        }
    }
}
