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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stellarelite.xingyuzhenlv.i18n.t
import com.stellarelite.xingyuzhenlv.network.OrderDailyTrip
import com.stellarelite.xingyuzhenlv.network.SupabaseClient
import com.stellarelite.xingyuzhenlv.network.UserSession
import com.stellarelite.xingyuzhenlv.update.openLegalUrl
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import xingyuzhenlv.composeapp.generated.resources.Res
import xingyuzhenlv.composeapp.generated.resources.splash_logo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MultiDayCharterScreen(onBack: () -> Unit = {}) {
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()

    // 联系人信息
    var whatsappCountryCode by remember { mutableStateOf("+60") }
    var whatsappCountryName by remember { mutableStateOf("马来西亚") }
    var countryCodeExpanded by remember { mutableStateOf(false) }
    var countrySearch by remember { mutableStateOf("") }
    var whatsappPhone by remember { mutableStateOf("") }
    var wechatId by remember { mutableStateOf("") }

    // 人数与行李
    var adults by remember { mutableIntStateOf(1) }
    var children by remember { mutableIntStateOf(0) }
    var luggage by remember { mutableIntStateOf(0) }

    // 预约行程（开始/结束日期时间）
    var showStartDatePicker by remember { mutableStateOf(false) }
    var showStartTimePicker by remember { mutableStateOf(false) }
    var showEndDatePicker by remember { mutableStateOf(false) }
    var showEndTimePicker by remember { mutableStateOf(false) }
    var startDateText by remember { mutableStateOf("") }
    var startTimeText by remember { mutableStateOf("") }
    var endDateText by remember { mutableStateOf("") }
    var endTimeText by remember { mutableStateOf("") }

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

    // 多日包车
    var specialNotes by remember { mutableStateOf("") }

    // 预估价格
    var showPrice by remember { mutableStateOf(false) }

    // 下单确认弹窗
    var showConfirmDialog by remember { mutableStateOf(false) }
    var submitting by remember { mutableStateOf(false) }
    var submitError by remember { mutableStateOf<String?>(null) }
    var submitSuccess by remember { mutableStateOf(false) }
    var agreeTerms by remember { mutableStateOf(false) }
    var agreePrivacy by remember { mutableStateOf(false) }
    var agreeRefund by remember { mutableStateOf(false) }
    var agreePaymentFee by remember { mutableStateOf(false) }
    var showLegalDialog by remember { mutableStateOf<String?>(null) }

    // 自动计算包车时长（开始日期时间 → 结束日期时间）
    val charterDuration = calcCharterDuration(startDateText, startTimeText, endDateText, endTimeText)

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
                text = t("multi_day_charter_title"),
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
                    // 可搜索区号选择器
                    ExposedDropdownMenuBox(
                        expanded = countryCodeExpanded,
                        onExpandedChange = {
                            countryCodeExpanded = it
                            if (it) countrySearch = ""
                        }
                    ) {
                        OutlinedTextField(
                            value = if (countryCodeExpanded) countrySearch else "$whatsappCountryCode $whatsappCountryName",
                            onValueChange = { countrySearch = it },
                            label = { Text(t("booking_country_code")) },
                            modifier = Modifier.width(160.dp).menuAnchor(),
                            singleLine = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = countryCodeExpanded) }
                        )
                        val filtered = if (countrySearch.isBlank()) countryCodes
                            else countryCodes.filter {
                                it.name.contains(countrySearch, ignoreCase = true) ||
                                it.code.contains(countrySearch)
                            }
                        ExposedDropdownMenu(
                            expanded = countryCodeExpanded,
                            onDismissRequest = { countryCodeExpanded = false }
                        ) {
                            filtered.take(50).forEach { country ->
                                DropdownMenuItem(
                                    text = { Text("${country.code}  ${country.name}") },
                                    onClick = {
                                        whatsappCountryCode = country.code
                                        whatsappCountryName = country.name
                                        countryCodeExpanded = false
                                        countrySearch = ""
                                    }
                                )
                            }
                        }
                    }
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
                    onClick = { /* TODO: 使用已存档微信号 */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Outlined.History, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("使用已存档微信号")
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
                // 开始日期 | 开始时间
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(modifier = Modifier.weight(1f).clip(RoundedCornerShape(4.dp)).clickable { showStartDatePicker = true }) {
                        OutlinedTextField(
                            value = startDateText,
                            onValueChange = {},
                            label = { Text(t("booking_start_date")) },
                            readOnly = true,
                            enabled = false,
                            modifier = Modifier.fillMaxWidth(),
                            trailingIcon = { Icon(Icons.Outlined.CalendarMonth, null, tint = MaterialTheme.colorScheme.primary) },
                            colors = OutlinedTextFieldDefaults.colors(
                                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                                disabledBorderColor = MaterialTheme.colorScheme.outline,
                                disabledLabelColor = MaterialTheme.colorScheme.outline
                            )
                        )
                    }
                    Box(modifier = Modifier.weight(1f).clip(RoundedCornerShape(4.dp)).clickable { showStartTimePicker = true }) {
                        OutlinedTextField(
                            value = startTimeText,
                            onValueChange = {},
                            label = { Text(t("booking_start_time")) },
                            readOnly = true,
                            enabled = false,
                            modifier = Modifier.fillMaxWidth(),
                            trailingIcon = { Icon(Icons.Outlined.Schedule, null, tint = MaterialTheme.colorScheme.primary) },
                            colors = OutlinedTextFieldDefaults.colors(
                                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                                disabledBorderColor = MaterialTheme.colorScheme.outline,
                                disabledLabelColor = MaterialTheme.colorScheme.outline
                            )
                        )
                    }
                }
                Spacer(Modifier.height(12.dp))
                // 结束日期 | 结束时间
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(modifier = Modifier.weight(1f).clip(RoundedCornerShape(4.dp)).clickable { showEndDatePicker = true }) {
                        OutlinedTextField(
                            value = endDateText,
                            onValueChange = {},
                            label = { Text(t("booking_end_date")) },
                            readOnly = true,
                            enabled = false,
                            modifier = Modifier.fillMaxWidth(),
                            trailingIcon = { Icon(Icons.Outlined.CalendarMonth, null, tint = MaterialTheme.colorScheme.primary) },
                            colors = OutlinedTextFieldDefaults.colors(
                                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                                disabledBorderColor = MaterialTheme.colorScheme.outline,
                                disabledLabelColor = MaterialTheme.colorScheme.outline
                            )
                        )
                    }
                    Box(modifier = Modifier.weight(1f).clip(RoundedCornerShape(4.dp)).clickable { showEndTimePicker = true }) {
                        OutlinedTextField(
                            value = endTimeText,
                            onValueChange = {},
                            label = { Text(t("booking_end_time")) },
                            readOnly = true,
                            enabled = false,
                            modifier = Modifier.fillMaxWidth(),
                            trailingIcon = { Icon(Icons.Outlined.Schedule, null, tint = MaterialTheme.colorScheme.primary) },
                            colors = OutlinedTextFieldDefaults.colors(
                                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                                disabledBorderColor = MaterialTheme.colorScheme.outline,
                                disabledLabelColor = MaterialTheme.colorScheme.outline
                            )
                        )
                    }
                }
                Spacer(Modifier.height(12.dp))
                // 自动计算包车时长
                Text(
                    "⏱️ ${t("booking_charter")}: ${charterDuration.days} ${t("booking_days")} | ${charterDuration.hours} ${t("booking_hours")}",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        // 开始日期选择器
        if (showStartDatePicker) {
            val datePickerState = rememberDatePickerState()
            DatePickerDialog(
                onDismissRequest = { showStartDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            startDateText = formatDate(millis)
                        }
                        showStartDatePicker = false
                    }) { Text(t("booking_confirm")) }
                },
                dismissButton = {
                    TextButton(onClick = { showStartDatePicker = false }) { Text(t("booking_cancel")) }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }

        // 开始时间选择器
        if (showStartTimePicker) {
            val timePickerState = rememberTimePickerState(
                initialHour = 12,
                initialMinute = 0,
                is24Hour = true
            )
            AlertDialog(
                onDismissRequest = { showStartTimePicker = false },
                title = { Text(t("booking_select_time")) },
                text = {
                    TimePicker(state = timePickerState)
                },
                confirmButton = {
                    TextButton(onClick = {
                        startTimeText = "${timePickerState.hour.toString().padStart(2, '0')}:${timePickerState.minute.toString().padStart(2, '0')}"
                        showStartTimePicker = false
                    }) { Text(t("booking_confirm")) }
                },
                dismissButton = {
                    TextButton(onClick = { showStartTimePicker = false }) { Text(t("booking_cancel")) }
                }
            )
        }

        // 结束日期选择器
        if (showEndDatePicker) {
            val datePickerState = rememberDatePickerState()
            DatePickerDialog(
                onDismissRequest = { showEndDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            endDateText = formatDate(millis)
                        }
                        showEndDatePicker = false
                    }) { Text(t("booking_confirm")) }
                },
                dismissButton = {
                    TextButton(onClick = { showEndDatePicker = false }) { Text(t("booking_cancel")) }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }

        // 结束时间选择器
        if (showEndTimePicker) {
            val timePickerState = rememberTimePickerState(
                initialHour = 12,
                initialMinute = 0,
                is24Hour = true
            )
            AlertDialog(
                onDismissRequest = { showEndTimePicker = false },
                title = { Text(t("booking_select_time")) },
                text = {
                    TimePicker(state = timePickerState)
                },
                confirmButton = {
                    TextButton(onClick = {
                        endTimeText = "${timePickerState.hour.toString().padStart(2, '0')}:${timePickerState.minute.toString().padStart(2, '0')}"
                        showEndTimePicker = false
                    }) { Text(t("booking_confirm")) }
                },
                dismissButton = {
                    TextButton(onClick = { showEndTimePicker = false }) { Text(t("booking_cancel")) }
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

        // ========== 多日包车设置 ==========
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("🛣️ ${t("multi_day_charter_settings")}", fontWeight = FontWeight.Bold, fontSize = 16.sp)
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
                    Text("⏱️ ${t("booking_duration_based")}", fontSize = 14.sp)
                }
            }
        }

        // ========== 确认行程按钮 ==========
        Button(
            onClick = {
                showPrice = true
                showConfirmDialog = true
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

        // 下单确认弹窗
        if (showConfirmDialog) {
            val allAgreed = agreeTerms && agreePrivacy && agreeRefund && agreePaymentFee
            AlertDialog(
                onDismissRequest = {
                    showConfirmDialog = false
                    agreeTerms = false; agreePrivacy = false
                    agreeRefund = false; agreePaymentFee = false
                },
                title = { Text("确认下单", fontWeight = FontWeight.Bold) },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        ConsentCheckRow(
                            checked = agreeTerms,
                            onCheckedChange = { agreeTerms = it },
                            label = "我已阅读并同意",
                            linkText = "《服务使用协议》",
                            onLinkClick = { showLegalDialog = "terms" }
                        )
                        ConsentCheckRow(
                            checked = agreePrivacy,
                            onCheckedChange = { agreePrivacy = it },
                            label = "我已阅读并同意",
                            linkText = "《隐私政策》",
                            onLinkClick = { showLegalDialog = "privacy" }
                        )
                        ConsentCheckRow(
                            checked = agreeRefund,
                            onCheckedChange = { agreeRefund = it },
                            label = "我已阅读并同意",
                            linkText = "《预订退款及取消政策》",
                            onLinkClick = { showLegalDialog = "refund" }
                        )
                        ConsentCheckRow(
                            checked = agreePaymentFee,
                            onCheckedChange = { agreePaymentFee = it },
                            label = "我已阅读并同意",
                            linkText = "《支付通道服务费说明》",
                            onLinkClick = { showLegalDialog = "payment" }
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            "已知悉订单将收取6%综合支付通道服务费。",
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f)
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            showConfirmDialog = false
                            scope.launch {
                                submitting = true
                                submitError = null
                                val userId = UserSession.userId ?: UserSession.generateGuestId()
                                val order = OrderDailyTrip(
                                    user_id = userId,
                                    whatsapp = whatsappCountryCode + whatsappPhone,
                                    wechat = wechatId,
                                    adult = adults,
                                    child = children,
                                    luggage = luggage,
                                    trip_start_date = buildTimestamp(startDateText, startTimeText),
                                    trip_end_date = buildTimestamp(endDateText, endTimeText),
                                    vehicle_count = vehicleCount,
                                    vehicle_type = selectedVehicle,
                                    departure_state = pickupState,
                                    departure_address = pickupAddresses.getOrNull(0)?.detailAddress,
                                    destination_state = dropoffState,
                                    destination_address = dropoffAddresses.getOrNull(0)?.detailAddress,
                                    notes = specialNotes.ifBlank { null },
                                    departure_address_2 = pickupAddresses.getOrNull(1)?.detailAddress,
                                    departure_address_3 = pickupAddresses.getOrNull(2)?.detailAddress,
                                    departure_address_4 = pickupAddresses.getOrNull(3)?.detailAddress,
                                    departure_address_5 = pickupAddresses.getOrNull(4)?.detailAddress,
                                    departure_address_6 = pickupAddresses.getOrNull(5)?.detailAddress,
                                    departure_address_7 = pickupAddresses.getOrNull(6)?.detailAddress,
                                    departure_address_8 = pickupAddresses.getOrNull(7)?.detailAddress,
                                    departure_address_9 = pickupAddresses.getOrNull(8)?.detailAddress,
                                    departure_address_10 = pickupAddresses.getOrNull(9)?.detailAddress,
                                    destination_address_2 = dropoffAddresses.getOrNull(1)?.detailAddress,
                                    destination_address_3 = dropoffAddresses.getOrNull(2)?.detailAddress,
                                    destination_address_4 = dropoffAddresses.getOrNull(3)?.detailAddress,
                                    destination_address_5 = dropoffAddresses.getOrNull(4)?.detailAddress,
                                    destination_address_6 = dropoffAddresses.getOrNull(5)?.detailAddress,
                                    destination_address_7 = dropoffAddresses.getOrNull(6)?.detailAddress,
                                    destination_address_8 = dropoffAddresses.getOrNull(7)?.detailAddress,
                                    destination_address_9 = dropoffAddresses.getOrNull(8)?.detailAddress,
                                    destination_address_10 = dropoffAddresses.getOrNull(9)?.detailAddress
                                )
                                val result = SupabaseClient.createOrderDailyTrip(order)
                                submitting = false
                                if (result != null) {
                                    submitSuccess = true
                                } else {
                                    submitError = "下单失败，请检查网络后重试"
                                }
                            }
                        },
                        enabled = allAgreed && !submitting
                    ) {
                        Text(if (submitting) "提交中..." else "确认下单")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showConfirmDialog = false
                        agreeTerms = false; agreePrivacy = false
                        agreeRefund = false; agreePaymentFee = false
                    }) {
                        Text("取消")
                    }
                }
            )
        }

        Spacer(Modifier.height(100.dp))
    }

    // 法律协议内容弹窗
    showLegalDialog?.let { section ->
        LegalContentDialog(section = section, onDismiss = { showLegalDialog = null })
    }

    // 下单成功弹窗
    if (submitSuccess) {
        AlertDialog(
            onDismissRequest = { submitSuccess = false },
            title = { Text("下单成功", fontWeight = FontWeight.Bold) },
            text = { Text("订单已提交，我们会尽快与您联系。") },
            confirmButton = {
                Button(onClick = { submitSuccess = false; onBack() }) { Text("好的") }
            }
        )
    }

    // 下单失败弹窗
    submitError?.let { msg ->
        AlertDialog(
            onDismissRequest = { submitError = null },
            title = { Text("下单失败", fontWeight = FontWeight.Bold) },
            text = { Text(msg) },
            confirmButton = {
                TextButton(onClick = { submitError = null }) { Text("重试") }
            }
        )
    }
}

private fun buildTimestamp(date: String, time: String): String {
    if (date.isBlank()) return ""
    val t = if (time.isBlank()) "00:00" else time
    return "${date}T${t}:00+08:00"
}

data class CharterDuration(val days: Int, val hours: Int)

private fun formatDate(millis: Long): String {
    val cal = java.util.Calendar.getInstance().apply { timeInMillis = millis }
    return "${cal.get(java.util.Calendar.YEAR)}-${(cal.get(java.util.Calendar.MONTH) + 1).toString().padStart(2, '0')}-${cal.get(java.util.Calendar.DAY_OF_MONTH).toString().padStart(2, '0')}"
}

private fun calcCharterDuration(startDate: String, startTime: String, endDate: String, endTime: String): CharterDuration {
    if (startDate.isBlank() || endDate.isBlank()) return CharterDuration(0, 0)
    val st = (if (startTime.isBlank()) "00:00" else startTime).split(":")
    val et = (if (endTime.isBlank()) "00:00" else endTime).split(":")
    return try {
        val s = startDate.split("-")
        val e = endDate.split("-")
        val startCal = java.util.Calendar.getInstance().apply {
            set(s[0].toInt(), s[1].toInt() - 1, s[2].toInt(), st[0].toInt(), st[1].toInt(), 0)
        }
        val endCal = java.util.Calendar.getInstance().apply {
            set(e[0].toInt(), e[1].toInt() - 1, e[2].toInt(), et[0].toInt(), et[1].toInt(), 0)
        }
        val diffHours = ((endCal.timeInMillis - startCal.timeInMillis) / (1000 * 60 * 60)).toInt()
        if (diffHours < 0) CharterDuration(0, 0) else CharterDuration(diffHours / 24, diffHours % 24)
    } catch (_: Exception) {
        CharterDuration(0, 0)
    }
}


