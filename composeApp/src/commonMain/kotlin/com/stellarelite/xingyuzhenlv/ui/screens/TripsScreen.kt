package com.stellarelite.xingyuzhenlv.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import com.stellarelite.xingyuzhenlv.network.OrderAmount
import com.stellarelite.xingyuzhenlv.network.OrderDailyTrip
import com.stellarelite.xingyuzhenlv.network.OrderTrip
import com.stellarelite.xingyuzhenlv.network.SupabaseClient
import com.stellarelite.xingyuzhenlv.network.UserSession
import kotlinx.coroutines.launch

enum class TripTab { ALL, BOOKED, HISTORY, CANCELLED }

data class TripRecord(
    val orderNo: String,
    val status: String,
    val statusText: String,
    val statusColor: Color,
    val tab: TripTab,
    val dateDisplay: String,
    val departureState: String,
    val destinationState: String,
    val finalAmount: Double,
    val oneWay: OrderTrip? = null,
    val daily: OrderDailyTrip? = null,
    val amount: OrderAmount? = null
)

fun statusInfo(status: String?): Pair<String, Color> = when (status) {
    "Pick-up" -> t("trip_status_pickup") to Color(0xFF4CAF50)
    "Complete" -> t("trip_status_completed") to Color(0xFF9E9E9E)
    "Cancel" -> t("trip_status_cancelled") to Color(0xFFF44336)
    "Confirm" -> t("trip_status_confirmed") to Color(0xFF2196F3)
    else -> (status ?: "-") to Color(0xFF9E9E9E)
}

fun tabForStatus(status: String?): TripTab = when (status) {
    "Complete" -> TripTab.HISTORY
    "Cancel" -> TripTab.CANCELLED
    else -> TripTab.BOOKED // Confirm / Pick-up 视为已预约
}

fun formatIsoDateTime(iso: String?): String {
    if (iso.isNullOrBlank()) return ""
    val date = iso.substringBefore('T')
    val time = iso.substringAfter('T', "").take(5)
    return if (date.isNotBlank() && time.isNotBlank()) "$date $time" else date
}

@Composable
fun TripsScreen(onViewDetail: (TripRecord) -> Unit = {}) {
    var selectedTab by remember { mutableStateOf(TripTab.ALL) }
    var trips by remember { mutableStateOf<List<TripRecord>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        loading = true
        val userId = UserSession.userId
        val result = if (userId != null) {
            val oneWay = SupabaseClient.getOrderTrips(userId)
            val daily = SupabaseClient.getOrderDailyTrips(userId)
            val amounts = SupabaseClient.getOrderAmounts(userId).associateBy { it.order_no }
            buildList {
                oneWay.forEach { o ->
                    add(buildTripRecord(o, null, amounts[o.order_no]))
                }
                daily.forEach { d ->
                    add(buildTripRecord(null, d, amounts[d.order_no]))
                }
            }.sortedByDescending { it.dateDisplay }
        } else emptyList()
        trips = result
        loading = false
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

        TabRow(
            selectedTabIndex = selectedTab.ordinal,
            containerColor = MaterialTheme.colorScheme.background,
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            listOf(t("tab_all"), t("tab_booked"), t("tab_history"), t("tab_cancelled")).forEachIndexed { index, title ->
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

        when {
            loading -> Box(modifier = Modifier.fillMaxWidth().padding(40.dp), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            filtered.isEmpty() -> Box(modifier = Modifier.fillMaxWidth().padding(40.dp), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Filled.AirportShuttle, null, tint = MaterialTheme.colorScheme.outline, modifier = Modifier.size(48.dp))
                    Spacer(Modifier.height(8.dp))
                    Text(t("no_trips_found"), color = MaterialTheme.colorScheme.outline)
                }
            }
            else -> Column(modifier = Modifier.verticalScroll(rememberScrollState()).padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                filtered.forEach { trip -> TripCard(trip, onViewDetail = { onViewDetail(trip) }) }
                Spacer(Modifier.height(80.dp))
            }
        }
    }
}

private fun buildTripRecord(oneWay: OrderTrip?, daily: OrderDailyTrip?, amount: OrderAmount?): TripRecord {
    val orderNo = oneWay?.order_no ?: daily?.order_no ?: ""
    val status = oneWay?.status ?: daily?.status
    val (statusText, statusColor) = statusInfo(status)
    val dateDisplay = if (oneWay != null) {
        formatIsoDateTime(oneWay.trips_date)
    } else {
        val s = formatIsoDateTime(daily?.trip_start_date)
        val e = formatIsoDateTime(daily?.trip_end_date)
        if (s.isNotBlank() && e.isNotBlank()) "$s ~ $e" else (if (s.isNotBlank()) s else e)
    }
    return TripRecord(
        orderNo = orderNo,
        status = status ?: "",
        statusText = statusText,
        statusColor = statusColor,
        tab = tabForStatus(status),
        dateDisplay = dateDisplay,
        departureState = oneWay?.departure_state ?: daily?.departure_state ?: "",
        destinationState = oneWay?.destination_state ?: daily?.destination_state ?: "",
        finalAmount = amount?.final_amount ?: 0.0,
        oneWay = oneWay,
        daily = daily,
        amount = amount
    )
}

@Composable
private fun TripCard(trip: TripRecord, onViewDetail: () -> Unit = {}) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // 订单号 + 状态
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("${t("order_no")}: ${trip.orderNo}", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface)
                Surface(shape = RoundedCornerShape(12.dp), color = trip.statusColor.copy(alpha = 0.12f)) {
                    Text(trip.statusText, Modifier.padding(horizontal = 10.dp, vertical = 3.dp),
                        fontSize = 12.sp, color = trip.statusColor, fontWeight = FontWeight.Medium)
                }
            }

            Spacer(Modifier.height(8.dp))

            // 日期
            if (trip.dateDisplay.isNotBlank()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Schedule, null, tint = MaterialTheme.colorScheme.outline, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(4.dp))
                    Text(trip.dateDisplay, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface)
                }
                Spacer(Modifier.height(8.dp))
            }

            // 出发地
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.size(8.dp).background(Color(0xFF4CAF50), CircleShape))
                Spacer(Modifier.width(8.dp))
                Text("${t("departure_state")}: ${trip.departureState}", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.weight(1f))
            }

            Spacer(Modifier.height(4.dp))
            Box(Modifier.width(1.dp).height(16.dp).padding(start = 3.dp).background(MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)))

            // 目的地
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.size(8.dp).background(Color(0xFFF44336), CircleShape))
                Spacer(Modifier.width(8.dp))
                Text("${t("destination_state")}: ${trip.destinationState}", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.weight(1f))
            }

            Spacer(Modifier.height(10.dp))

            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))

            Spacer(Modifier.height(8.dp))

            // 最终金额 + 查看详情
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Column {
                    Text(t("final_amount"), fontSize = 11.sp, color = MaterialTheme.colorScheme.outline)
                    Text(CurrencyManager.formatPrice(trip.finalAmount), fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                }
                OutlinedButton(
                    onClick = onViewDetail,
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp)
                ) {
                    Text(t("view_details"), fontSize = 13.sp, fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}
