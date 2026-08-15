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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stellarelite.xingyuzhenlv.i18n.t
import com.stellarelite.xingyuzhenlv.network.OrderDailyTrip
import com.stellarelite.xingyuzhenlv.network.OrderTrip

@Composable
fun TripDetailScreen(trip: TripRecord, onBack: () -> Unit = {}) {
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
                    Text(trip.statusText, Modifier.padding(horizontal = 10.dp, vertical = 3.dp),
                        fontSize = 12.sp, color = trip.statusColor, fontWeight = FontWeight.Medium)
                }
            }
        }

        // 订单号
        if (trip.orderNo.isNotBlank()) {
            DetailCard(t("order_no")) {
                DetailRow(t("order_no"), trip.orderNo)
            }
        }

        val oneWay = trip.oneWay
        val daily = trip.daily

        if (oneWay != null) {
            renderOneWayFields(oneWay)
        } else if (daily != null) {
            renderDailyFields(daily)
        }

        // 金额
        trip.amount?.let { renderAmountFields(it) }

        Spacer(Modifier.height(80.dp))
    }
}

@Composable
private fun renderOneWayFields(o: OrderTrip) {
    // 预约时间
    if (o.trips_date.isNotBlank()) {
        DetailCard(t("trip_datetime")) {
            DetailRow(t("trip_datetime"), formatIsoDateTime(o.trips_date))
        }
    }

    // 行程详情
    DetailCard("📍 ${t("trip_origin")} / ${t("trip_destination")}") {
        DetailRow(t("departure_state"), o.departure_state)
        collectAddresses(o.departure_address, o.departure_address_2, o.departure_address_3, o.departure_address_4, o.departure_address_5, o.departure_address_6, o.departure_address_7, o.departure_address_8, o.departure_address_9, o.departure_address_10)
            .forEachIndexed { i, addr -> DetailRow("${t("booking_pickup_address")} ${i + 1}", addr) }
        DetailRow(t("destination_state"), o.destination_state)
        collectAddresses(o.destination_address, o.destination_address_2, o.destination_address_3, o.destination_address_4, o.destination_address_5, o.destination_address_6, o.destination_address_7, o.destination_address_8, o.destination_address_9, o.destination_address_10)
            .forEachIndexed { i, addr -> DetailRow("${t("booking_dropoff_address")} ${i + 1}", addr) }
    }

    // 联系信息
    if (o.whatsapp.isNotBlank() || o.wechat.isNotBlank()) {
        DetailCard("📞 联系信息") {
            if (o.whatsapp.isNotBlank()) DetailRow("WhatsApp", o.whatsapp)
            if (o.wechat.isNotBlank()) DetailRow("WeChat", o.wechat)
        }
    }

    // 人数与行李
    DetailCard("👥 ${t("booking_passengers_luggage")}") {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            CountBlock(t("booking_adults"), o.adult)
            CountBlock(t("booking_children"), o.child)
            CountBlock(t("booking_luggage"), o.luggage)
        }
    }

    // 车辆
    DetailCard("🚗 ${t("booking_vehicle_type")}") {
        if (o.vehicle_type.isNotBlank()) DetailRow(t("booking_vehicle_type"), o.vehicle_type)
        DetailRow(t("booking_vehicle_count"), "${o.vehicle_count} ${t("booking_vehicles")}")
    }

    // 备注
    if (!o.notes.isNullOrBlank()) {
        DetailCard("📝 ${t("booking_special_notes")}") {
            Text(o.notes, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface)
        }
    }
}

@Composable
private fun renderDailyFields(d: OrderDailyTrip) {
    // 预约时间
    DetailCard(t("trip_datetime")) {
        if (d.trip_start_date.isNotBlank()) DetailRow(t("booking_start_date"), formatIsoDateTime(d.trip_start_date))
        if (d.trip_end_date.isNotBlank()) DetailRow(t("booking_end_date"), formatIsoDateTime(d.trip_end_date))
    }

    // 行程详情
    DetailCard("📍 ${t("trip_origin")} / ${t("trip_destination")}") {
        if (!d.departure_state.isNullOrBlank()) DetailRow(t("departure_state"), d.departure_state)
        collectAddresses(d.departure_address, d.departure_address_2, d.departure_address_3, d.departure_address_4, d.departure_address_5, d.departure_address_6, d.departure_address_7, d.departure_address_8, d.departure_address_9, d.departure_address_10)
            .forEachIndexed { i, addr -> DetailRow("${t("booking_pickup_address")} ${i + 1}", addr) }
        if (!d.destination_state.isNullOrBlank()) DetailRow(t("destination_state"), d.destination_state)
        collectAddresses(d.destination_address, d.destination_address_2, d.destination_address_3, d.destination_address_4, d.destination_address_5, d.destination_address_6, d.destination_address_7, d.destination_address_8, d.destination_address_9, d.destination_address_10)
            .forEachIndexed { i, addr -> DetailRow("${t("booking_dropoff_address")} ${i + 1}", addr) }
    }

    // 联系信息
    if (!d.whatsapp.isNullOrBlank() || !d.wechat.isNullOrBlank()) {
        DetailCard("📞 联系信息") {
            if (!d.whatsapp.isNullOrBlank()) DetailRow("WhatsApp", d.whatsapp)
            if (!d.wechat.isNullOrBlank()) DetailRow("WeChat", d.wechat)
        }
    }

    // 人数与行李
    DetailCard("👥 ${t("booking_passengers_luggage")}") {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            CountBlock(t("booking_adults"), d.adult)
            CountBlock(t("booking_children"), d.child)
            CountBlock(t("booking_luggage"), d.luggage)
        }
    }

    // 车辆
    DetailCard("🚗 ${t("booking_vehicle_type")}") {
        if (!d.vehicle_type.isNullOrBlank()) DetailRow(t("booking_vehicle_type"), d.vehicle_type)
        DetailRow(t("booking_vehicle_count"), "${d.vehicle_count} ${t("booking_vehicles")}")
    }

    // 备注
    if (!d.notes.isNullOrBlank()) {
        DetailCard("📝 ${t("booking_special_notes")}") {
            Text(d.notes, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface)
        }
    }
}

@Composable
private fun renderAmountFields(a: com.stellarelite.xingyuzhenlv.network.OrderAmount) {
    DetailCard("💰 ${t("final_amount")}") {
        if (a.base_price != null) DetailRow(t("base_price"), CurrencyManager.formatPrice(a.base_price))
        if (a.car_upgrade_fee != null) DetailRow(t("car_upgrade_fee"), CurrencyManager.formatPrice(a.car_upgrade_fee))
        if (a.car_reduce_fee != null) DetailRow(t("car_reduce_fee"), CurrencyManager.formatPrice(a.car_reduce_fee))
        if (a.discount != null) DetailRow(t("discount"), CurrencyManager.formatPrice(a.discount))
    }
    // 最终金额高亮
    if (a.final_amount != null) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.8f))
        ) {
            Column(Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(t("final_amount"), fontSize = 14.sp, color = MaterialTheme.colorScheme.primary)
                Spacer(Modifier.height(8.dp))
                Text(CurrencyManager.formatPrice(a.final_amount), fontSize = 28.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

private fun collectAddresses(vararg addresses: String?): List<String> =
    addresses.mapNotNull { it?.takeIf { s -> s.isNotBlank() } }

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
