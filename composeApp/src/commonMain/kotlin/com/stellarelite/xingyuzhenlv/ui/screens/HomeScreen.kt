package com.stellarelite.xingyuzhenlv.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stellarelite.xingyuzhenlv.i18n.t
import com.stellarelite.xingyuzhenlv.model.Trip
import com.stellarelite.xingyuzhenlv.model.TripStatus

@Composable
fun HomeScreen(onBookTrip: () -> Unit = {}, onCrossBorder: () -> Unit = {}, onNotification: () -> Unit = {}) {
    var activeTrip by remember {
        mutableStateOf(
            Trip(
                id = "TRIP-001",
                dateTime = "2026-08-07 14:30",
                origin = "星域臻旅总部",
                destination = "KLIA 吉隆坡国际机场",
                driverName = "陈师傅",
                licensePlate = "WVF 8888",
                carModel = "Toyota Alphard",
                amount = "RM 168.00",
                driverLat = 3.1150,
                driverLng = 101.6650,
                originLat = 3.1200,
                originLng = 101.6700,
                status = TripStatus.DRIVER_ON_THE_WAY
            )
        )
    }

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
    ) {
        AdBanner()
        Spacer(modifier = Modifier.height(16.dp))
        QuickActionsRow(onBookTrip = onBookTrip, onCrossBorder = onCrossBorder, onNotification = onNotification)
        Spacer(modifier = Modifier.height(20.dp))
        if (activeTrip != null) {
            TripCard(trip = activeTrip!!)
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = { /* TODO: open driver location map */ },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(Icons.Filled.MyLocation, null, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(t("view_driver_location"), fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
            }
        } else {
            EmptyTripCard()
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun AdBanner() {
    Box(
        modifier = Modifier.fillMaxWidth().height(180.dp).padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Brush.horizontalGradient(listOf(
                MaterialTheme.colorScheme.primary,
                MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)))),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(Icons.Filled.Campaign, null, tint = Color.White, modifier = Modifier.size(36.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(t("home_ad_title"), color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(t("home_ad_subtitle"), color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp)
        }
    }
}

@Composable
private fun QuickActionsRow(onBookTrip: () -> Unit, onCrossBorder: () -> Unit, onNotification: () -> Unit) {
    Row(Modifier.fillMaxWidth().padding(horizontal = 16.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
        QuickActionButton(Icons.Filled.CalendarMonth, t("home_book_trip"), MaterialTheme.colorScheme.primary, onClick = onBookTrip)
        QuickActionButton(Icons.Filled.SupportAgent, t("home_contact_support"), Color(0xFF4CAF50))
        QuickActionButton(Icons.Filled.Public, t("home_cross_border"), Color(0xFFFF9800), onClick = onCrossBorder)
        QuickActionButton(Icons.Filled.Notifications, "通知", Color(0xFF9C27B0), onClick = onNotification)
    }
}

@Composable
private fun QuickActionButton(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, color: Color, onClick: () -> Unit = {}) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable { onClick() }.padding(8.dp)) {
        Box(Modifier.size(56.dp).clip(CircleShape).background(color.copy(alpha = 0.1f)), contentAlignment = Alignment.Center) {
            Icon(icon, label, tint = color, modifier = Modifier.size(28.dp))
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(label, fontSize = 13.sp, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onBackground)
    }
}

@Composable
private fun TripCard(trip: Trip) {
    Card(Modifier.fillMaxWidth().padding(horizontal = 16.dp), shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(t("trip_title"), fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                Surface(shape = RoundedCornerShape(20.dp), color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)) {
                    Text(trip.status.name, Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
            Spacer(modifier = Modifier.height(16.dp))
            TripInfoRow(Icons.Filled.Schedule, t("trip_datetime"), trip.dateTime)
            Spacer(modifier = Modifier.height(12.dp))
            TripInfoRow(Icons.Filled.TripOrigin, t("trip_origin"), trip.origin, Color(0xFF4CAF50))
            Spacer(modifier = Modifier.height(12.dp))
            TripInfoRow(Icons.Filled.LocationOn, t("trip_destination"), trip.destination, Color(0xFFF44336))
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
            Spacer(modifier = Modifier.height(16.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column { DriverInfoItem(t("trip_driver"), trip.driverName); DriverInfoItem(t("trip_plate"), trip.licensePlate) }
                Column(horizontalAlignment = Alignment.End) { DriverInfoItem(t("trip_car"), trip.carModel); DriverInfoItem(t("trip_amount"), trip.amount) }
            }
        }
    }
}

@Composable
private fun TripInfoRow(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, value: String, iconTint: Color = MaterialTheme.colorScheme.primary) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, null, tint = iconTint, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text("$label: ", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
        Text(value, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onSurface)
    }
}

@Composable
private fun DriverInfoItem(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 2.dp)) {
        Text(label, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))
        Text(value, fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurface)
    }
}

@Composable
private fun EmptyTripCard() {
    Card(Modifier.fillMaxWidth().padding(horizontal = 16.dp), shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)) {
        Column(Modifier.fillMaxWidth().padding(40.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(Icons.Filled.AirportShuttle, null, tint = MaterialTheme.colorScheme.outline, modifier = Modifier.size(48.dp))
            Spacer(modifier = Modifier.height(12.dp))
            Text(t("no_trip"), fontSize = 16.sp, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))
            Text(t("no_trip_hint"), fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f))
        }
    }
}
