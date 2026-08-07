package com.stellarelite.xingyuzhenlv.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stellarelite.xingyuzhenlv.i18n.t
import org.jetbrains.compose.resources.painterResource
import xingyuzhenlv.composeapp.generated.resources.Res
import xingyuzhenlv.composeapp.generated.resources.splash_logo

// 西马11州 + 新加坡
val malaysiaStates = listOf(
    "Perlis", "Kedah", "Penang", "Perak", "Selangor",
    "Negeri Sembilan", "Malacca", "Johor", "Pahang",
    "Terengganu", "Kelantan", "Singapore"
)

// 车辆类型
val vehicleTypes = listOf(
    "任何四座车辆",
    "任何多用途车辆",
    "任何多用途商务车",
    "日产 赛瑞娜 7座",
    "丰田 普瑞维亚 7座",
    "丰田 普瑞维亚 8座",
    "丰田 英诺娃 8座",
    "丰田 威尔法 2代 8座",
    "丰田 威尔法 2代 7座",
    "丰田 威尔法 2代 商务座",
    "丰田 埃尔法 1代 8座",
    "丰田 埃尔法 2代 8座",
    "丰田 埃尔法 2代 7座",
    "丰田 埃尔法 2代 商务座",
    "丰田 埃尔法 3代 8座",
    "丰田 埃尔法 3代 7座",
    "丰田 埃尔法 3代 商务座",
    "丰田 埃尔法 4代 商务座",
    "现代 辉翼 10座",
    "现代 星侠 10座"
)

data class AddressEntry(
    val state: String = "",
    val detailAddress: String = ""
)

data class DateSelection(
    val day: Int = 1, val month: Int = 1, val year: Int = 2026
)

data class TimeSelection(
    val hour: Int = 12, val minute: Int = 0
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingScreen(onBack: () -> Unit = {}) {
    val scrollState = rememberScrollState()

    // 联系人信息
    var whatsappCountryCode by remember { mutableStateOf("60") }
    var whatsappPhone by remember { mutableStateOf("") }
    var wechatId by remember { mutableStateOf("") }

    // 人数与行李
    var adults by remember { mutableIntStateOf(1) }
    var children by remember { mutableIntStateOf(0) }
    var luggage by remember { mutableIntStateOf(0) }

    // 预约行程
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var dateSelection by remember { mutableStateOf(DateSelection()) }
    var timeSelection by remember { mutableStateOf(TimeSelection()) }
    var dateText by remember { mutableStateOf("") }
    var timeText by remember { mutableStateOf("") }

    // 上车地址
    var pickupStatesExpanded by remember { mutableStateOf(false) }
    var pickupState by remember { mutableStateOf("") }
    var pickupAddresses by remember { mutableStateOf(mutableListOf(AddressEntry())) }

    // 下车地址
    var dropoffStatesExpanded by remember { mutableStateOf(false) }
    var dropoffState by remember { mutableStateOf("") }
    var dropoffAddresses by remember { mutableStateOf(mutableListOf(AddressEntry())) }

    // 车辆
    var vehicleCountExpanded by remember { mutableStateOf(false) }
    var vehicleCount by remember { mutableIntStateOf(1) }
    var vehicleTypeExpanded by remember { mutableStateOf(false) }
    var selectedVehicle by remember { mutableStateOf("") }

    // 行程类型
    var tripTypeExpanded by remember { mutableStateOf(false) }
    var tripType by remember { mutableStateOf("") }
    var specialNotes by remember { mutableStateOf("") }
    var showCharterOptions by remember { mutableStateOf(false) }
    var charterHours by remember { mutableIntStateOf(1) }
    var charterMultiDay by remember { mutableStateOf(false) }
    var charterStartDate by remember { mutableStateOf("") }
    var charterEndDate by remember { mutableStateOf("") }

    // 预估价格
    var showPrice by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 返回按钮
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(Icons.Filled.ArrowBack, contentDescription = t("booking_cancel"))
            }
            Spacer(Modifier.width(8.dp))
            Text(
                text = t("booking_title"),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // 标题（保留旧标题以防其他引用）

        // ========== 联系WhatsApp卡片 ==========
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("📱 WhatsApp", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = whatsappCountryCode,
                        onValueChange = { if (it.length <= 4) whatsappCountryCode = it },
                        label = { Text(t("booking_country_code")) },
                        modifier = Modifier.width(90.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                    )
                    Spacer(Modifier.width(8.dp))
                    OutlinedTextField(
                        value = whatsappPhone,
                        onValueChange = { whatsappPhone = it },
                        label = { Text(t("booking_phone")) },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                    )
                }
                Spacer(Modifier.height(8.dp))
                OutlinedButton(
                    onClick = { /* TODO: 使用已存档手机号 */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Outlined.History, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text(t("booking_use_saved_phone"))
                }
            }
        }

        // ========== 联系微信卡片 ==========
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("💬 WeChat", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = wechatId,
                    onValueChange = { wechatId = it },
                    label = { Text(t("booking_wechat_id")) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                OutlinedButton(
                    onClick = { /* TODO: 使用已存档手机号 */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Outlined.History, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text(t("booking_use_saved_phone"))
                }
            }
        }

        // ========== 人数与行李卡片 ==========
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("👥 ${t("booking_passengers_luggage")}", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    CounterColumn(label = t("booking_adults"), value = adults, onMinus = { if (adults > 0) adults-- }, onPlus = { adults++ })
                    CounterColumn(label = t("booking_children"), value = children, onMinus = { if (children > 0) children-- }, onPlus = { children++ })
                    CounterColumn(label = t("booking_luggage"), value = luggage, onMinus = { if (luggage > 0) luggage-- }, onPlus = { luggage++ })
                }
            }
        }

        // ========== 预约行程卡片 ==========
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("📅 ${t("booking_schedule")}", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = dateText,
                        onValueChange = {},
                        label = { Text(t("booking_date")) },
                        readOnly = true,
                        modifier = Modifier.weight(1f).clickable { showDatePicker = true },
                        trailingIcon = { Icon(Icons.Outlined.CalendarMonth, null) }
                    )
                    OutlinedTextField(
                        value = timeText,
                        onValueChange = {},
                        label = { Text(t("booking_time")) },
                        readOnly = true,
                        modifier = Modifier.weight(1f).clickable { showTimePicker = true },
                        trailingIcon = { Icon(Icons.Outlined.Schedule, null) }
                    )
                }
            }
        }

        // 日期选择器
        if (showDatePicker) {
            val datePickerState = rememberDatePickerState()
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val calendar = java.util.Calendar.getInstance().apply { timeInMillis = millis }
                            dateSelection = DateSelection(
                                calendar.get(java.util.Calendar.DAY_OF_MONTH),
                                calendar.get(java.util.Calendar.MONTH) + 1,
                                calendar.get(java.util.Calendar.YEAR)
                            )
                            dateText = "${dateSelection.year}-${dateSelection.month.toString().padStart(2, '0')}-${dateSelection.day.toString().padStart(2, '0')}"
                        }
                        showDatePicker = false
                    }) { Text(t("booking_confirm")) }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) { Text(t("booking_cancel")) }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }

        // 时间选择器
        if (showTimePicker) {
            val timePickerState = rememberTimePickerState(
                initialHour = timeSelection.hour,
                initialMinute = timeSelection.minute,
                is24Hour = true
            )
            AlertDialog(
                onDismissRequest = { showTimePicker = false },
                title = { Text(t("booking_select_time")) },
                text = {
                    TimePicker(state = timePickerState)
                },
                confirmButton = {
                    TextButton(onClick = {
                        timeSelection = TimeSelection(timePickerState.hour, timePickerState.minute)
                        timeText = "${timeSelection.hour.toString().padStart(2, '0')}:${timeSelection.minute.toString().padStart(2, '0')}"
                        showTimePicker = false
                    }) { Text(t("booking_confirm")) }
                },
                dismissButton = {
                    TextButton(onClick = { showTimePicker = false }) { Text(t("booking_cancel")) }
                }
            )
        }

        // ========== 行程详情卡片 ==========
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("📍 ${t("booking_trip_details")}", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(Modifier.height(12.dp))

                // 上车地址
                Text("🟢 ${t("booking_pickup")}", fontWeight = FontWeight.Medium, fontSize = 14.sp)
                Spacer(Modifier.height(8.dp))
                // 州选择
                ExposedDropdownMenuBox(
                    expanded = pickupStatesExpanded,
                    onExpandedChange = { pickupStatesExpanded = it }
                ) {
                    OutlinedTextField(
                        value = pickupState,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text(t("booking_select_state")) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = pickupStatesExpanded) },
                        modifier = Modifier.fillMaxWidth().menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = pickupStatesExpanded,
                        onDismissRequest = { pickupStatesExpanded = false }
                    ) {
                        malaysiaStates.forEach { state ->
                            DropdownMenuItem(
                                text = { Text(state) },
                                onClick = {
                                    pickupState = state
                                    pickupStatesExpanded = false
                                }
                            )
                        }
                    }
                }
                Spacer(Modifier.height(8.dp))
                // 上车地址列表
                pickupAddresses.forEachIndexed { index, entry ->
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            OutlinedTextField(
                                value = entry.detailAddress,
                                onValueChange = { newVal ->
                                    pickupAddresses = pickupAddresses.toMutableList().also { it[index] = it[index].copy(detailAddress = newVal) }
                                },
                                label = { Text("${t("booking_pickup_address")} ${index + 1}") },
                                modifier = Modifier.weight(1f)
                            )
                            IconButton(onClick = {
                                // TODO: 定位快捷填充
                            }) {
                                Icon(Icons.Filled.MyLocation, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            }
                        }
                        Spacer(Modifier.height(4.dp))
                    }
                }
                // +按钮
                if (pickupAddresses.size < 10) {
                    TextButton(onClick = {
                        pickupAddresses = pickupAddresses.toMutableList().also { it.add(AddressEntry()) }
                    }) {
                        Icon(Icons.Outlined.Add, null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(4.dp))
                        Text(t("booking_add_address"))
                    }
                }

                Spacer(Modifier.height(16.dp))

                // 下车地址
                Text("🔴 ${t("booking_dropoff")}", fontWeight = FontWeight.Medium, fontSize = 14.sp)
                Spacer(Modifier.height(8.dp))
                ExposedDropdownMenuBox(
                    expanded = dropoffStatesExpanded,
                    onExpandedChange = { dropoffStatesExpanded = it }
                ) {
                    OutlinedTextField(
                        value = dropoffState,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text(t("booking_select_state")) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropoffStatesExpanded) },
                        modifier = Modifier.fillMaxWidth().menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = dropoffStatesExpanded,
                        onDismissRequest = { dropoffStatesExpanded = false }
                    ) {
                        malaysiaStates.forEach { state ->
                            DropdownMenuItem(
                                text = { Text(state) },
                                onClick = {
                                    dropoffState = state
                                    dropoffStatesExpanded = false
                                }
                            )
                        }
                    }
                }
                Spacer(Modifier.height(8.dp))
                dropoffAddresses.forEachIndexed { index, entry ->
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            OutlinedTextField(
                                value = entry.detailAddress,
                                onValueChange = { newVal ->
                                    dropoffAddresses = dropoffAddresses.toMutableList().also { it[index] = it[index].copy(detailAddress = newVal) }
                                },
                                label = { Text("${t("booking_dropoff_address")} ${index + 1}") },
                                modifier = Modifier.weight(1f)
                            )
                            IconButton(onClick = {
                                // TODO: 定位快捷填充
                            }) {
                                Icon(Icons.Filled.MyLocation, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            }
                        }
                        Spacer(Modifier.height(4.dp))
                    }
                }
                if (dropoffAddresses.size < 10) {
                    TextButton(onClick = {
                        dropoffAddresses = dropoffAddresses.toMutableList().also { it.add(AddressEntry()) }
                    }) {
                        Icon(Icons.Outlined.Add, null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(4.dp))
                        Text(t("booking_add_address"))
                    }
                }
            }
        }

        // ========== 类型车辆卡片 ==========
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("🚗 ${t("booking_vehicle_type")}", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(Modifier.height(12.dp))

                // 车辆数量
                ExposedDropdownMenuBox(
                    expanded = vehicleCountExpanded,
                    onExpandedChange = { vehicleCountExpanded = it }
                ) {
                    OutlinedTextField(
                        value = "$vehicleCount ${t("booking_vehicles")}",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text(t("booking_vehicle_count")) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = vehicleCountExpanded) },
                        modifier = Modifier.fillMaxWidth().menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = vehicleCountExpanded,
                        onDismissRequest = { vehicleCountExpanded = false }
                    ) {
                        (1..10).forEach { count ->
                            DropdownMenuItem(
                                text = { Text("$count") },
                                onClick = {
                                    vehicleCount = count
                                    vehicleCountExpanded = false
                                }
                            )
                        }
                    }
                }
                Spacer(Modifier.height(12.dp))

                // 车辆类型
                ExposedDropdownMenuBox(
                    expanded = vehicleTypeExpanded,
                    onExpandedChange = { vehicleTypeExpanded = it }
                ) {
                    OutlinedTextField(
                        value = selectedVehicle,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text(t("booking_vehicle_type")) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = vehicleTypeExpanded) },
                        modifier = Modifier.fillMaxWidth().menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = vehicleTypeExpanded,
                        onDismissRequest = { vehicleTypeExpanded = false }
                    ) {
                        vehicleTypes.forEach { v ->
                            DropdownMenuItem(
                                text = { Text(v) },
                                onClick = {
                                    selectedVehicle = v
                                    vehicleTypeExpanded = false
                                }
                            )
                        }
                    }
                }
            }
        }

        // ========== 行程类型 ==========
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("🛣️ ${t("booking_trip_type")}", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(Modifier.height(12.dp))

                ExposedDropdownMenuBox(
                    expanded = tripTypeExpanded,
                    onExpandedChange = { tripTypeExpanded = it }
                ) {
                    OutlinedTextField(
                        value = tripType,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text(t("booking_trip_type")) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = tripTypeExpanded) },
                        modifier = Modifier.fillMaxWidth().menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = tripTypeExpanded,
                        onDismissRequest = { tripTypeExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(t("booking_one_way")) },
                            onClick = {
                                tripType = t("booking_one_way")
                                showCharterOptions = false
                                tripTypeExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(t("booking_charter")) },
                            onClick = {
                                tripType = t("booking_charter")
                                showCharterOptions = true
                                tripTypeExpanded = false
                            }
                        )
                    }
                }

                Spacer(Modifier.height(12.dp))

                // 包车选项
                AnimatedVisibility(visible = showCharterOptions) {
                    Column {
                        // 自定义时长
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(t("booking_hours"), modifier = Modifier.width(80.dp))
                            IconButton(onClick = { if (charterHours > 1) charterHours-- }) {
                                Icon(Icons.Filled.Remove, null)
                            }
                            Text("$charterHours", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                            IconButton(onClick = { if (charterHours < 24) charterHours++ }) {
                                Icon(Icons.Filled.Add, null)
                            }
                        }
                        Spacer(Modifier.height(8.dp))

                        // 自定义多日
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = charterMultiDay,
                                onCheckedChange = { charterMultiDay = it }
                            )
                            Text(t("booking_multi_day"))
                        }
                        AnimatedVisibility(visible = charterMultiDay) {
                            Column {
                                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                    OutlinedTextField(
                                        value = charterStartDate,
                                        onValueChange = { charterStartDate = it },
                                        label = { Text(t("booking_start_date")) },
                                        modifier = Modifier.weight(1f)
                                    )
                                    OutlinedTextField(
                                        value = charterEndDate,
                                        onValueChange = { charterEndDate = it },
                                        label = { Text(t("booking_end_date")) },
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(Modifier.height(12.dp))

                // 特别需求
                OutlinedTextField(
                    value = specialNotes,
                    onValueChange = { if (it.length <= 500) specialNotes = it },
                    label = { Text(t("booking_special_notes")) },
                    modifier = Modifier.fillMaxWidth().heightIn(min = 100.dp),
                    maxLines = 5
                )
                Text(
                    "${specialNotes.length}/500",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }

        // ========== 预估价格卡片 ==========
        AnimatedVisibility(visible = showPrice) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.8f)
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("💰 ${t("booking_estimated_price")}", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Spacer(Modifier.height(4.dp))
                    Text(
                        t("booking_price_note"),
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.outline
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "RM XXX - XXX",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    if (vehicleCount > 1) {
                        Text("🚗 ×$vehicleCount ${t("booking_vehicles")}", fontSize = 14.sp)
                    }
                    if (showCharterOptions || charterMultiDay) {
                        Text("⏱️ ${t("booking_duration_based")}", fontSize = 14.sp)
                    }
                }
            }
        }

        // ========== 确认行程按钮 ==========
        Button(
            onClick = {
                showPrice = true
                // TODO: 提交行程请求
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(
                t("booking_confirm_trip"),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.height(100.dp))
    }
}

@Composable
fun CounterColumn(
    label: String,
    value: Int,
    onMinus: () -> Unit,
    onPlus: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, fontSize = 13.sp, color = MaterialTheme.colorScheme.outline)
        Spacer(Modifier.height(4.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onMinus, modifier = Modifier.size(32.dp)) {
                Icon(Icons.Filled.Remove, null, modifier = Modifier.size(18.dp))
            }
            Text(
                "$value",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.widthIn(min = 30.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            IconButton(onClick = onPlus, modifier = Modifier.size(32.dp)) {
                Icon(Icons.Filled.Add, null, modifier = Modifier.size(18.dp))
            }
        }
    }
}
