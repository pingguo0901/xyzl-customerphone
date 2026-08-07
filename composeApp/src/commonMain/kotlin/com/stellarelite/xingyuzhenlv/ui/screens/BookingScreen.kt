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

data class CountryCode(val code: String, val name: String)

val countryCodes = listOf(
    CountryCode("+1", "加拿大"), CountryCode("+1", "美国"),
    CountryCode("+7", "俄罗斯"), CountryCode("+20", "埃及"),
    CountryCode("+27", "南非"), CountryCode("+30", "希腊"),
    CountryCode("+31", "荷兰"), CountryCode("+32", "比利时"),
    CountryCode("+33", "法国"), CountryCode("+34", "西班牙"),
    CountryCode("+36", "匈牙利"), CountryCode("+39", "意大利"),
    CountryCode("+40", "罗马尼亚"), CountryCode("+41", "瑞士"),
    CountryCode("+43", "奥地利"), CountryCode("+44", "英国"),
    CountryCode("+45", "丹麦"), CountryCode("+46", "瑞典"),
    CountryCode("+47", "挪威"), CountryCode("+48", "波兰"),
    CountryCode("+49", "德国"), CountryCode("+51", "秘鲁"),
    CountryCode("+52", "墨西哥"), CountryCode("+53", "古巴"),
    CountryCode("+54", "阿根廷"), CountryCode("+55", "巴西"),
    CountryCode("+56", "智利"), CountryCode("+57", "哥伦比亚"),
    CountryCode("+58", "委内瑞拉"), CountryCode("+60", "马来西亚"),
    CountryCode("+61", "澳大利亚"), CountryCode("+62", "印度尼西亚"),
    CountryCode("+63", "菲律宾"), CountryCode("+64", "新西兰"),
    CountryCode("+65", "新加坡"), CountryCode("+66", "泰国"),
    CountryCode("+81", "日本"), CountryCode("+82", "韩国"),
    CountryCode("+84", "越南"), CountryCode("+86", "中国大陆"),
    CountryCode("+90", "土耳其"), CountryCode("+91", "印度"),
    CountryCode("+92", "巴基斯坦"), CountryCode("+93", "阿富汗"),
    CountryCode("+94", "斯里兰卡"), CountryCode("+95", "缅甸"),
    CountryCode("+98", "伊朗"), CountryCode("+211", "南苏丹"),
    CountryCode("+212", "摩洛哥"), CountryCode("+213", "阿尔及利亚"),
    CountryCode("+216", "突尼斯"), CountryCode("+218", "利比亚"),
    CountryCode("+220", "冈比亚"), CountryCode("+221", "塞内加尔"),
    CountryCode("+222", "毛里塔尼亚"), CountryCode("+223", "马里"),
    CountryCode("+224", "几内亚"), CountryCode("+225", "科特迪瓦"),
    CountryCode("+226", "布基纳法索"), CountryCode("+227", "尼日尔"),
    CountryCode("+228", "多哥"), CountryCode("+229", "贝宁"),
    CountryCode("+230", "毛里求斯"), CountryCode("+231", "利比里亚"),
    CountryCode("+232", "塞拉利昂"), CountryCode("+233", "加纳"),
    CountryCode("+234", "尼日利亚"), CountryCode("+235", "乍得"),
    CountryCode("+236", "中非"), CountryCode("+237", "喀麦隆"),
    CountryCode("+238", "佛得角"), CountryCode("+239", "圣多美和普林西比"),
    CountryCode("+240", "赤道几内亚"), CountryCode("+241", "加蓬"),
    CountryCode("+242", "刚果共和国"), CountryCode("+243", "刚果民主共和国"),
    CountryCode("+244", "安哥拉"), CountryCode("+245", "几内亚比绍"),
    CountryCode("+246", "英属印度洋领地"), CountryCode("+247", "阿森松岛"),
    CountryCode("+248", "塞舌尔"), CountryCode("+249", "苏丹"),
    CountryCode("+250", "卢旺达"), CountryCode("+251", "埃塞俄比亚"),
    CountryCode("+252", "索马里"), CountryCode("+253", "吉布提"),
    CountryCode("+254", "肯尼亚"), CountryCode("+255", "坦桑尼亚"),
    CountryCode("+256", "乌干达"), CountryCode("+257", "布隆迪"),
    CountryCode("+258", "莫桑比克"), CountryCode("+260", "赞比亚"),
    CountryCode("+261", "马达加斯加"), CountryCode("+262", "留尼汪"),
    CountryCode("+263", "津巴布韦"), CountryCode("+264", "纳米比亚"),
    CountryCode("+265", "马拉维"), CountryCode("+266", "莱索托"),
    CountryCode("+267", "博茨瓦纳"), CountryCode("+268", "斯威士兰"),
    CountryCode("+269", "科摩罗"), CountryCode("+290", "圣赫勒拿"),
    CountryCode("+290-8", "特里斯坦-达库尼亚"), CountryCode("+291", "厄立特里亚"),
    CountryCode("+297", "阿鲁巴"), CountryCode("+298", "法罗群岛"),
    CountryCode("+299", "格陵兰"), CountryCode("+350", "直布罗陀"),
    CountryCode("+351", "葡萄牙"), CountryCode("+352", "卢森堡"),
    CountryCode("+353", "爱尔兰"), CountryCode("+354", "冰岛"),
    CountryCode("+355", "阿尔巴尼亚"), CountryCode("+356", "马耳他"),
    CountryCode("+357", "塞浦路斯"), CountryCode("+358", "芬兰"),
    CountryCode("+358-18", "奥兰"), CountryCode("+359", "保加利亚"),
    CountryCode("+370", "立陶宛"), CountryCode("+371", "拉脱维亚"),
    CountryCode("+372", "爱沙尼亚"), CountryCode("+373", "摩尔多瓦"),
    CountryCode("+374", "亚美尼亚"), CountryCode("+375", "白俄罗斯"),
    CountryCode("+376", "安道尔"), CountryCode("+377", "摩纳哥"),
    CountryCode("+378", "圣马力诺"), CountryCode("+380", "乌克兰"),
    CountryCode("+381", "塞尔维亚"), CountryCode("+382", "黑山"),
    CountryCode("+383", "科索沃"), CountryCode("+385", "克罗地亚"),
    CountryCode("+386", "斯洛文尼亚"), CountryCode("+387", "波黑"),
    CountryCode("+389", "北马其顿"), CountryCode("+420", "捷克"),
    CountryCode("+421", "斯洛伐克"), CountryCode("+423", "列支敦士登"),
    CountryCode("+500", "福克兰群岛"), CountryCode("+501", "伯利兹"),
    CountryCode("+502", "危地马拉"), CountryCode("+503", "萨尔瓦多"),
    CountryCode("+504", "洪都拉斯"), CountryCode("+505", "尼加拉瓜"),
    CountryCode("+506", "哥斯达黎加"), CountryCode("+507", "巴拿马"),
    CountryCode("+508", "圣皮埃尔和密克隆"), CountryCode("+509", "海地"),
    CountryCode("+590", "瓜德罗普"), CountryCode("+591", "玻利维亚"),
    CountryCode("+592", "圭亚那"), CountryCode("+593", "厄瓜多尔"),
    CountryCode("+594", "法属圭亚那"), CountryCode("+595", "巴拉圭"),
    CountryCode("+596", "马提尼克"), CountryCode("+597", "苏里南"),
    CountryCode("+598", "乌拉圭"), CountryCode("+670", "东帝汶"),
    CountryCode("+672", "澳大利亚海外领地"), CountryCode("+673", "文莱"),
    CountryCode("+674", "瑙鲁"), CountryCode("+675", "巴布亚新几内亚"),
    CountryCode("+676", "汤加"), CountryCode("+677", "所罗门群岛"),
    CountryCode("+678", "瓦努阿图"), CountryCode("+679", "斐济"),
    CountryCode("+680", "帕劳"), CountryCode("+681", "瓦利斯和富图纳"),
    CountryCode("+682", "库克群岛"), CountryCode("+683", "纽埃"),
    CountryCode("+685", "萨摩亚"), CountryCode("+686", "基里巴斯"),
    CountryCode("+687", "新喀里多尼亚"), CountryCode("+688", "图瓦卢"),
    CountryCode("+689", "法属波利尼西亚"), CountryCode("+690", "托克劳"),
    CountryCode("+691", "密克罗尼西亚联邦"), CountryCode("+692", "马绍尔群岛"),
    CountryCode("+850", "朝鲜"), CountryCode("+852", "香港"),
    CountryCode("+853", "澳门"), CountryCode("+855", "柬埔寨"),
    CountryCode("+856", "老挝"), CountryCode("+880", "孟加拉国"),
    CountryCode("+886", "台湾"), CountryCode("+960", "马尔代夫"),
    CountryCode("+961", "黎巴嫩"), CountryCode("+962", "约旦"),
    CountryCode("+963", "叙利亚"), CountryCode("+964", "伊拉克"),
    CountryCode("+965", "科威特"), CountryCode("+966", "沙特阿拉伯"),
    CountryCode("+967", "也门"), CountryCode("+968", "阿曼"),
    CountryCode("+970", "巴勒斯坦"), CountryCode("+971", "阿联酋"),
    CountryCode("+972", "以色列"), CountryCode("+973", "巴林"),
    CountryCode("+974", "卡塔尔"), CountryCode("+975", "不丹"),
    CountryCode("+976", "蒙古国"), CountryCode("+977", "尼泊尔"),
    CountryCode("+992", "塔吉克斯坦"), CountryCode("+993", "土库曼斯坦"),
    CountryCode("+994", "阿塞拜疆"), CountryCode("+995", "格鲁吉亚"),
    CountryCode("+996", "吉尔吉斯斯坦"), CountryCode("+998", "乌兹别克斯坦")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingScreen(onBack: () -> Unit = {}) {
    val scrollState = rememberScrollState()

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
    var showCharterStartDatePicker by remember { mutableStateOf(false) }
    var showCharterEndDatePicker by remember { mutableStateOf(false) }

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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(modifier = Modifier.weight(1f).clip(RoundedCornerShape(4.dp)).clickable { showDatePicker = true }) {
                        OutlinedTextField(
                            value = dateText,
                            onValueChange = {},
                            label = { Text(t("booking_date")) },
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
                    Box(modifier = Modifier.weight(1f).clip(RoundedCornerShape(4.dp)).clickable { showTimePicker = true }) {
                        OutlinedTextField(
                            value = timeText,
                            onValueChange = {},
                            label = { Text(t("booking_time")) },
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

        // 包车开始日期选择器
        if (showCharterStartDatePicker) {
            val datePickerState = rememberDatePickerState()
            DatePickerDialog(
                onDismissRequest = { showCharterStartDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val cal = java.util.Calendar.getInstance().apply { timeInMillis = millis }
                            charterStartDate = "${cal.get(java.util.Calendar.YEAR)}-${(cal.get(java.util.Calendar.MONTH) + 1).toString().padStart(2, '0')}-${cal.get(java.util.Calendar.DAY_OF_MONTH).toString().padStart(2, '0')}"
                        }
                        showCharterStartDatePicker = false
                    }) { Text(t("booking_confirm")) }
                },
                dismissButton = {
                    TextButton(onClick = { showCharterStartDatePicker = false }) { Text(t("booking_cancel")) }
                }
            ) { DatePicker(state = datePickerState) }
        }

        // 包车结束日期选择器
        if (showCharterEndDatePicker) {
            val datePickerState = rememberDatePickerState()
            DatePickerDialog(
                onDismissRequest = { showCharterEndDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val cal = java.util.Calendar.getInstance().apply { timeInMillis = millis }
                            charterEndDate = "${cal.get(java.util.Calendar.YEAR)}-${(cal.get(java.util.Calendar.MONTH) + 1).toString().padStart(2, '0')}-${cal.get(java.util.Calendar.DAY_OF_MONTH).toString().padStart(2, '0')}"
                        }
                        showCharterEndDatePicker = false
                    }) { Text(t("booking_confirm")) }
                },
                dismissButton = {
                    TextButton(onClick = { showCharterEndDatePicker = false }) { Text(t("booking_cancel")) }
                }
            ) { DatePicker(state = datePickerState) }
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
                                    Box(modifier = Modifier.weight(1f).clip(RoundedCornerShape(4.dp)).clickable { showCharterStartDatePicker = true }) {
                                        OutlinedTextField(
                                            value = charterStartDate,
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
                                    Box(modifier = Modifier.weight(1f).clip(RoundedCornerShape(4.dp)).clickable { showCharterEndDatePicker = true }) {
                                        OutlinedTextField(
                                            value = charterEndDate,
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
