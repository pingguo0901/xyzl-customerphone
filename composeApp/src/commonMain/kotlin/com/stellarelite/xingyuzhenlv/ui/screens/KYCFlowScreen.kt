package com.stellarelite.xingyuzhenlv.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

fun stepLabel(step: Int) = when (step) {
    1 -> "上传证件"
    2 -> "个人资料"
    3 -> "联系资料"
    4 -> "人脸识别"
    5 -> "确认资料"
    else -> ""
}

val docTypes = listOf("国际护照", "马来西亚身份证", "马来西亚居住证", "马来西亚工作证")
val genderOptions = listOf("男", "女", "其他")

val countryList = listOf(
    "马来西亚", "新加坡", "中国", "印度", "印度尼西亚", "泰国", "越南", "菲律宾",
    "日本", "韩国", "美国", "英国", "加拿大", "澳大利亚", "法国", "德国",
    "俄罗斯", "巴西", "南非", "沙特阿拉伯", "阿联酋", "巴基斯坦", "孟加拉国",
    "缅甸", "柬埔寨", "老挝", "文莱", "尼泊尔", "斯里兰卡", "蒙古国",
    "荷兰", "意大利", "西班牙", "瑞士", "瑞典", "丹麦", "挪威", "芬兰",
    "新西兰", "墨西哥", "埃及", "土耳其", "卡塔尔", "科威特", "伊朗", "伊拉克"
)

data class KyCCountryOption(val name: String, val code: String = "")

val kycCountryCodes = listOf(
    KyCCountryOption("马来西亚", "+60"), KyCCountryOption("新加坡", "+65"),
    KyCCountryOption("中国", "+86"), KyCCountryOption("印度", "+91"),
    KyCCountryOption("印度尼西亚", "+62"), KyCCountryOption("泰国", "+66"),
    KyCCountryOption("越南", "+84"), KyCCountryOption("菲律宾", "+63"),
    KyCCountryOption("日本", "+81"), KyCCountryOption("韩国", "+82"),
    KyCCountryOption("美国", "+1"), KyCCountryOption("英国", "+44"),
    KyCCountryOption("加拿大", "+1"), KyCCountryOption("澳大利亚", "+61"),
    KyCCountryOption("法国", "+33"), KyCCountryOption("德国", "+49"),
    KyCCountryOption("俄罗斯", "+7"), KyCCountryOption("巴西", "+55"),
    KyCCountryOption("南非", "+27"), KyCCountryOption("沙特阿拉伯", "+966"),
    KyCCountryOption("阿联酋", "+971"), KyCCountryOption("缅甸", "+95"),
    KyCCountryOption("柬埔寨", "+855"), KyCCountryOption("老挝", "+856"),
    KyCCountryOption("文莱", "+673"), KyCCountryOption("荷兰", "+31"),
    KyCCountryOption("意大利", "+39"), KyCCountryOption("西班牙", "+34")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KYCFlowScreen(onBack: () -> Unit = {}) {
    var currentStep by remember { mutableIntStateOf(1) }
    val scrollState = rememberScrollState()

    // Page 1
    var docTypeExpanded by remember { mutableStateOf(false) }
    var docType by remember { mutableStateOf("") }
    var docTypeIndex by remember { mutableIntStateOf(-1) }
    var docFrontSelected by remember { mutableStateOf(false) }
    var docBackSelected by remember { mutableStateOf(false) }
    var showDocFrontPicker by remember { mutableStateOf(false) }
    var showDocBackPicker by remember { mutableStateOf(false) }

    // Page 2
    var fullName by remember { mutableStateOf("") }
    var genderExpanded by remember { mutableStateOf(false) }
    var gender by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var showBirthDatePicker by remember { mutableStateOf(false) }
    var birthCountry by remember { mutableStateOf("") }
    var birthCountryExpanded by remember { mutableStateOf(false) }
    var nationality by remember { mutableStateOf("") }
    var nationalityExpanded by remember { mutableStateOf(false) }
    var docNumber by remember { mutableStateOf("") }
    var docExpiry by remember { mutableStateOf("") }
    var showDocExpiryPicker by remember { mutableStateOf(false) }

    // Page 3
    var residence by remember { mutableStateOf("") }
    var countryCode by remember { mutableStateOf("+60") }
    var countryCodeExpanded by remember { mutableStateOf(false) }
    var countryCodeSearch by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    // Page 4
    var faceScanComplete by remember { mutableStateOf(false) }
    var faceScanning by remember { mutableStateOf(false) }

    // Page 5
    var consentChecked by remember { mutableStateOf(false) }
    var showSubmitDialog by remember { mutableStateOf(false) }
    var showLegalDialog by remember { mutableStateOf<String?>(null) }

    val isPassport = docTypeIndex == 0
    val isOtherDoc = docTypeIndex in 1..3

    fun canProceed(step: Int): Boolean = when (step) {
        1 -> docType.isNotBlank() && docFrontSelected && docBackSelected
        2 -> fullName.isNotBlank() && gender.isNotBlank() && birthDate.isNotBlank() &&
             birthCountry.isNotBlank() && nationality.isNotBlank() && docNumber.isNotBlank() &&
             (if (isPassport) docExpiry.isNotBlank() else true)
        3 -> residence.isNotBlank() && phone.isNotBlank() && email.isNotBlank()
        4 -> faceScanComplete
        else -> true
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Top bar
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { if (currentStep == 1) onBack() else currentStep-- }) {
                Icon(Icons.Filled.ArrowBack, "返回")
            }
            Spacer(Modifier.width(8.dp))
            Text("KYC 实名认证", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }

        // Step indicator
        StepIndicator(currentStep)

        HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))

        // Content
        Column(
            modifier = Modifier.weight(1f).verticalScroll(scrollState).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            when (currentStep) {
                1 -> Page1DocumentUpload(
                    docType, docTypeExpanded, { docTypeExpanded = it },
                    onDocTypeSelect = { idx, label ->
                        docTypeIndex = idx; docType = label; docTypeExpanded = false
                        docFrontSelected = false; docBackSelected = false
                    },
                    docFrontSelected, docBackSelected,
                    onFrontClick = { showDocFrontPicker = true },
                    onBackClick = { showDocBackPicker = true },
                    isPassport = isPassport
                )
                2 -> Page2PersonalInfo(
                    fullName, { fullName = it }, isPassport,
                    gender, genderExpanded, { genderExpanded = it },
                    { gender = it; genderExpanded = false },
                    birthDate, showBirthDatePicker,
                    { showBirthDatePicker = true }, { showBirthDatePicker = false },
                    birthCountry, birthCountryExpanded, { birthCountryExpanded = it },
                    { birthCountry = it },
                    nationality, nationalityExpanded, { nationalityExpanded = it },
                    { nationality = it },
                    docNumber, { docNumber = it }, isPassport,
                    docExpiry, showDocExpiryPicker,
                    { showDocExpiryPicker = true }, { showDocExpiryPicker = false },
                    docTypeIndex
                )
                3 -> Page3Contact(
                    residence, { residence = it },
                    countryCode, countryCodeExpanded, { countryCodeExpanded = it },
                    countryCodeSearch, { countryCodeSearch = it },
                    phone, { phone = it },
                    email, { email = it }
                )
                4 -> Page4FaceRecognition(
                    faceScanning, faceScanComplete,
                    onStartScan = { faceScanning = true },
                    onComplete = { faceScanning = false; faceScanComplete = true }
                )
                5 -> Page5Confirm(
                    fullName = fullName, gender = gender, birthDate = birthDate,
                    birthCountry = birthCountry, nationality = nationality,
                    docNumber = docNumber, docExpiry = docExpiry,
                    residence = residence, countryCode = countryCode, phone = phone, email = email,
                    consentChecked = consentChecked,
                    onConsentChange = { consentChecked = it },
                    onShowLegal = { showLegalDialog = it },
                    isPassport = isPassport, docType = docType
                )
            }
        }

        // Bottom buttons
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (currentStep > 1) {
                OutlinedButton(
                    onClick = { currentStep-- },
                    modifier = Modifier.weight(1f).height(52.dp),
                    shape = RoundedCornerShape(14.dp)
                ) { Text("上一步") }
            }
            Button(
                onClick = {
                    if (currentStep < 5) currentStep++
                    else if (currentStep == 5 && consentChecked) showSubmitDialog = true
                },
                modifier = Modifier.weight(1f).height(52.dp),
                shape = RoundedCornerShape(14.dp),
                enabled = when (currentStep) {
                    5 -> consentChecked
                    else -> canProceed(currentStep)
                }
            ) {
                Text(if (currentStep < 5) "下一步" else "完成验证", fontSize = 16.sp, fontWeight = FontWeight.Medium)
            }
        }
    }

    // Date picker dialog
    if (showBirthDatePicker) {
        val datePickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { showBirthDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val cal = java.util.Calendar.getInstance().apply { timeInMillis = millis }
                        birthDate = "${cal.get(java.util.Calendar.YEAR)}-${(cal.get(java.util.Calendar.MONTH) + 1).toString().padStart(2, '0')}-${cal.get(java.util.Calendar.DAY_OF_MONTH).toString().padStart(2, '0')}"
                    }
                    showBirthDatePicker = false
                }) { Text("确认") }
            },
            dismissButton = { TextButton(onClick = { showBirthDatePicker = false }) { Text("取消") } }
        ) { DatePicker(state = datePickerState) }
    }

    // Doc expiry date picker
    if (showDocExpiryPicker) {
        val datePickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { showDocExpiryPicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val cal = java.util.Calendar.getInstance().apply { timeInMillis = millis }
                        docExpiry = "${cal.get(java.util.Calendar.YEAR)}-${(cal.get(java.util.Calendar.MONTH) + 1).toString().padStart(2, '0')}-${cal.get(java.util.Calendar.DAY_OF_MONTH).toString().padStart(2, '0')}"
                    }
                    showDocExpiryPicker = false
                }) { Text("确认") }
            },
            dismissButton = { TextButton(onClick = { showDocExpiryPicker = false }) { Text("取消") } }
        ) { DatePicker(state = datePickerState) }
    }

    // Doc front picker
    if (showDocFrontPicker) PhotoPickerDialog("${if (isPassport) "护照正面（人头像页）" else "证件正面"}") { showDocFrontPicker = false; docFrontSelected = true }

    // Doc back picker
    if (showDocBackPicker) PhotoPickerDialog("${if (isPassport) "护照外面（护照国家页面）" else "证件背面"}") { showDocBackPicker = false; docBackSelected = true }

    // Submit confirmation dialog
    if (showSubmitDialog) {
        AlertDialog(
            onDismissRequest = { showSubmitDialog = false },
            title = { Text("提交成功", fontWeight = FontWeight.Bold) },
            text = { Text("您的资料已交给工作人员审核，1个工作日里完成，稍后会通知您。", fontSize = 14.sp, lineHeight = 22.sp) },
            confirmButton = {
                TextButton(onClick = { showSubmitDialog = false; onBack() }) { Text("知道了") }
            }
        )
    }

    // Legal dialog
    showLegalDialog?.let { section ->
        LegalContentDialog(section = section, onDismiss = { showLegalDialog = null })
    }
}

/* ========== STEP INDICATOR ========== */

@Composable
private fun StepIndicator(currentStep: Int) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 1..5) {
            if (i > 1) {
                Box(
                    modifier = Modifier.weight(0.3f).height(2.dp)
                        .background(if (i <= currentStep) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Surface(
                    modifier = Modifier.size(28.dp),
                    shape = CircleShape,
                    color = if (i <= currentStep) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        if (i < currentStep) Icon(Icons.Filled.Check, null, tint = Color.White, modifier = Modifier.size(16.dp))
                        else Text("$i", fontSize = 13.sp, fontWeight = FontWeight.Bold,
                            color = if (i <= currentStep) Color.White else MaterialTheme.colorScheme.outline)
                    }
                }
                Spacer(Modifier.height(2.dp))
                Text(stepLabel(i), fontSize = 10.sp, color = if (i <= currentStep) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline)
            }
        }
    }
}

/* ========== PAGE 1: DOCUMENT UPLOAD ========== */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Page1DocumentUpload(
    docType: String, expanded: Boolean, onExpandedChange: (Boolean) -> Unit,
    onDocTypeSelect: (Int, String) -> Unit,
    frontSelected: Boolean, backSelected: Boolean,
    onFrontClick: () -> Unit, onBackClick: () -> Unit,
    isPassport: Boolean
) {
    Text("选择证件类型", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
    Spacer(Modifier.height(8.dp))

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = onExpandedChange) {
        OutlinedTextField(
            value = docType,
            onValueChange = {},
            readOnly = true,
            label = { Text("证件类型") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.fillMaxWidth().menuAnchor(),
            shape = RoundedCornerShape(12.dp)
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { onExpandedChange(false) }) {
            docTypes.forEachIndexed { idx, type ->
                DropdownMenuItem(text = { Text(type) }, onClick = { onDocTypeSelect(idx, type) })
            }
        }
    }

    if (docType.isNotBlank()) {
        Spacer(Modifier.height(10.dp))
        Text("上传证件照片", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.outline)
        Spacer(Modifier.height(8.dp))
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            UploadBox(
                label = if (isPassport) "护照正面（人头像页）" else "证件正面",
                selected = frontSelected,
                onClick = onFrontClick,
                modifier = Modifier.fillMaxWidth()
            )
            UploadBox(
                label = if (isPassport) "护照外面（护照国家页面）" else "证件背面",
                selected = backSelected,
                onClick = onBackClick,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun UploadBox(label: String, selected: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.height(140.dp),
        shape = RoundedCornerShape(12.dp),
        color = if (selected) Color(0xFFE8F5E9) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        border = ButtonDefaults.outlinedButtonBorder,
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (selected) {
                Icon(Icons.Filled.CheckCircle, null, tint = Color(0xFF4CAF50), modifier = Modifier.size(36.dp))
                Spacer(Modifier.height(8.dp))
                Text("已上传", fontSize = 12.sp, color = Color(0xFF4CAF50), fontWeight = FontWeight.Medium)
            } else {
                Icon(Icons.Filled.AddAPhoto, null, tint = MaterialTheme.colorScheme.outline, modifier = Modifier.size(32.dp))
                Spacer(Modifier.height(8.dp))
                Text(label, fontSize = 12.sp, color = MaterialTheme.colorScheme.outline, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
            }
        }
    }
}

@Composable
private fun PhotoPickerDialog(title: String, onDone: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDone,
        title = { Text(title, fontWeight = FontWeight.Bold) },
        text = {
            Column {
                TextButton(onClick = onDone) {
                    Icon(Icons.Filled.CameraAlt, null, modifier = Modifier.size(20.dp))
                    Spacer(Modifier.width(10.dp))
                    Text("拍照")
                }
                TextButton(onClick = onDone) {
                    Icon(Icons.Filled.PhotoLibrary, null, modifier = Modifier.size(20.dp))
                    Spacer(Modifier.width(10.dp))
                    Text("从相册选择")
                }
            }
        },
        confirmButton = {},
        dismissButton = { TextButton(onClick = onDone) { Text("取消") } }
    )
}

/* ========== PAGE 2: PERSONAL INFO ========== */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Page2PersonalInfo(
    fullName: String, onFullNameChange: (String) -> Unit, isPassport: Boolean,
    gender: String, genderExpanded: Boolean, onGenderExpanded: (Boolean) -> Unit,
    onGenderSelect: (String) -> Unit,
    birthDate: String, showBirthDatePicker: Boolean,
    onOpenBirthPicker: () -> Unit, onCloseBirthPicker: () -> Unit,
    birthCountry: String, birthCountryExpanded: Boolean, onBirthCountryExpanded: (Boolean) -> Unit,
    onBirthCountrySelect: (String) -> Unit,
    nationality: String, nationalityExpanded: Boolean, onNationalityExpanded: (Boolean) -> Unit,
    onNationalitySelect: (String) -> Unit,
    docNumber: String, onDocNumberChange: (String) -> Unit, showPassportFields: Boolean,
    docExpiry: String, showDocExpiryPicker: Boolean,
    onOpenExpiryPicker: () -> Unit, onCloseExpiryPicker: () -> Unit,
    docTypeIndex: Int
) {
    val labelPrefix = when (docTypeIndex) {
        0 -> "护照"
        1 -> "身份证"
        2 -> "居住证"
        3 -> "工作证"
        else -> "证件"
    }

    Text("$labelPrefix 个人资料", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
    Spacer(Modifier.height(4.dp))

    OutlinedTextField(
        value = fullName,
        onValueChange = { if (it.matches(Regex("^[a-zA-Z ]*$"))) onFullNameChange(it) },
        label = { Text("$labelPrefix 全名") },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Ascii)
    )

    // Gender
    ExposedDropdownMenuBox(expanded = genderExpanded, onExpandedChange = onGenderExpanded) {
        OutlinedTextField(
            value = gender, onValueChange = {}, readOnly = true,
            label = { Text("${labelPrefix}性别") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = genderExpanded) },
            modifier = Modifier.fillMaxWidth().menuAnchor(),
            shape = RoundedCornerShape(12.dp)
        )
        ExposedDropdownMenu(expanded = genderExpanded, onDismissRequest = { onGenderExpanded(false) }) {
            genderOptions.forEach { g ->
                DropdownMenuItem(text = { Text(g) }, onClick = { onGenderSelect(g) })
            }
        }
    }

    // Birth date
    OutlinedTextField(
        value = birthDate, onValueChange = {}, readOnly = true,
        label = { Text("出生日期") },
        modifier = Modifier.fillMaxWidth(),
        trailingIcon = { IconButton(onClick = onOpenBirthPicker) { Icon(Icons.Filled.CalendarMonth, null) } },
        shape = RoundedCornerShape(12.dp)
    )

    // Birth country
    SearchableDropdown(
        value = birthCountry, expanded = birthCountryExpanded,
        onExpandedChange = onBirthCountryExpanded,
        label = "出生国家/地", options = countryList,
        onSelect = onBirthCountrySelect
    )

    // Nationality
    SearchableDropdown(
        value = nationality, expanded = nationalityExpanded,
        onExpandedChange = onNationalityExpanded,
        label = "国籍/公民", options = countryList,
        onSelect = onNationalitySelect
    )

    // Document number
    OutlinedTextField(
        value = docNumber,
        onValueChange = { if (it.matches(Regex("^[a-zA-Z0-9]*$"))) onDocNumberChange(it) },
        label = { Text("${labelPrefix}号码") },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Ascii)
    )

    // Document expiry (passport only)
    if (isPassport) {
        OutlinedTextField(
            value = docExpiry, onValueChange = {}, readOnly = true,
            label = { Text("${labelPrefix}到期日期") },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = { IconButton(onClick = onOpenExpiryPicker) { Icon(Icons.Filled.CalendarMonth, null) } },
            shape = RoundedCornerShape(12.dp)
        )
    }
}

/* ========== PAGE 3: CONTACT ========== */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Page3Contact(
    residence: String, onResidenceChange: (String) -> Unit,
    countryCode: String, codeExpanded: Boolean, onCodeExpanded: (Boolean) -> Unit,
    codeSearch: String, onCodeSearch: (String) -> Unit,
    phone: String, onPhoneChange: (String) -> Unit,
    email: String, onEmailChange: (String) -> Unit
) {
    Text("联系资料", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
    Spacer(Modifier.height(4.dp))

    OutlinedTextField(
        value = residence, onValueChange = onResidenceChange,
        label = { Text("居住地") },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        shape = RoundedCornerShape(12.dp)
    )

    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        ExposedDropdownMenuBox(expanded = codeExpanded, onExpandedChange = { onCodeExpanded(it); if (it) onCodeSearch("") }) {
            OutlinedTextField(
                value = if (codeExpanded) codeSearch else countryCode,
                onValueChange = { onCodeSearch(it) },
                label = { Text("国家/地区代码") },
                modifier = Modifier.width(150.dp).menuAnchor(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                leadingIcon = { Text("+", fontSize = 16.sp, modifier = Modifier.padding(start = 4.dp)) }
            )
            val filtered = if (codeSearch.isBlank()) kycCountryCodes
            else kycCountryCodes.filter { it.name.contains(codeSearch, ignoreCase = true) || it.code.contains(codeSearch) }
            ExposedDropdownMenu(expanded = codeExpanded, onDismissRequest = { onCodeExpanded(false) }) {
                filtered.take(30).forEach { c ->
                    DropdownMenuItem(text = { Text("${c.name}  ${c.code}") }, onClick = {
                        onCodeExpanded(false); onCodeSearch("")
                    })
                }
            }
        }
        OutlinedTextField(
            value = phone, onValueChange = { if (it.all { c -> c.isDigit() }) onPhoneChange(it) },
            label = { Text("手机号码") },
            modifier = Modifier.weight(1f),
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )
    }

    OutlinedTextField(
        value = email, onValueChange = onEmailChange,
        label = { Text("电子邮箱") },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
    )
}

/* ========== PAGE 4: FACE RECOGNITION ========== */

@Composable
private fun Page4FaceRecognition(
    scanning: Boolean, complete: Boolean,
    onStartScan: () -> Unit, onComplete: () -> Unit
) {
    Text("人脸识别", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
    Spacer(Modifier.height(4.dp))
    Text("请将脸部对准取景框内，并跟随导航随动完成扫描", fontSize = 13.sp, color = MaterialTheme.colorScheme.outline)
    Spacer(Modifier.height(24.dp))

    Surface(
        modifier = Modifier.fillMaxWidth().height(280.dp),
        shape = RoundedCornerShape(20.dp),
        color = Color(0xFF1A1A2E),
        border = androidx.compose.foundation.BorderStroke(2.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.5f))
    ) {
        Box(contentAlignment = Alignment.Center) {
            if (complete) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Surface(
                        modifier = Modifier.size(64.dp),
                        shape = CircleShape,
                        color = Color(0xFF4CAF50)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(Icons.Filled.Check, null, tint = Color.White, modifier = Modifier.size(36.dp))
                        }
                    }
                    Spacer(Modifier.height(16.dp))
                    Text("扫描完成", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF4CAF50))
                }
            } else if (scanning) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator(modifier = Modifier.size(48.dp), color = Color.White)
                    Spacer(Modifier.height(16.dp))
                    Text("扫描中...", fontSize = 14.sp, color = Color.White.copy(alpha = 0.7f))
                }
            } else {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Filled.Face, null, tint = Color.White.copy(alpha = 0.4f), modifier = Modifier.size(64.dp))
                    Spacer(Modifier.height(16.dp))
                    Text("点击下方按钮开始人脸扫描", fontSize = 14.sp, color = Color.White.copy(alpha = 0.5f))
                }
            }
        }
    }

    Spacer(Modifier.height(16.dp))

    if (!complete) {
        Button(
            onClick = {
                if (scanning) onComplete() else onStartScan()
            },
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape = RoundedCornerShape(14.dp),
            enabled = !scanning
        ) {
            Text(if (scanning) "请稍候..." else "开始人脸扫描", fontSize = 16.sp, fontWeight = FontWeight.Medium)
        }
    } else {
        Text(
            "人脸识别已完成 ✓",
            fontSize = 14.sp, color = Color(0xFF4CAF50), fontWeight = FontWeight.Medium,
            modifier = Modifier.fillMaxWidth(),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}

/* ========== PAGE 5: CONFIRM ========== */

@Composable
private fun Page5Confirm(
    fullName: String, gender: String, birthDate: String,
    birthCountry: String, nationality: String, docNumber: String, docExpiry: String,
    residence: String, countryCode: String, phone: String, email: String,
    consentChecked: Boolean, onConsentChange: (Boolean) -> Unit,
    onShowLegal: (String?) -> Unit, isPassport: Boolean, docType: String
) {
    Text("确认资料", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
    Spacer(Modifier.height(4.dp))
    Text("请仔细核对以下信息，确认无误后提交", fontSize = 13.sp, color = MaterialTheme.colorScheme.outline)

    // Document card
    Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))) {
        Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text("$docType 详情", fontSize = 14.sp, fontWeight = FontWeight.Bold)
            ConfirmRow("$docType 全名", fullName)
            ConfirmRow("$docType 性别", gender)
            ConfirmRow("出生日期", birthDate)
            ConfirmRow("出生国家/地", birthCountry)
            ConfirmRow("国籍/公民", nationality)
            ConfirmRow("$docType 号码", docNumber)
            if (isPassport) ConfirmRow("$docType 到期日期", docExpiry)
        }
    }

    // Contact card
    Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))) {
        Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text("联系资料", fontSize = 14.sp, fontWeight = FontWeight.Bold)
            ConfirmRow("居住地", residence)
            ConfirmRow("国家/地区代码", "$countryCode $phone")
            ConfirmRow("电子邮箱", email)
        }
    }

    // Consent
    HorizontalDivider()
    Spacer(Modifier.height(8.dp))

    Row(
        verticalAlignment = Alignment.Top,
        modifier = Modifier.fillMaxWidth()
    ) {
        Checkbox(checked = consentChecked, onCheckedChange = onConsentChange)
        Column(modifier = Modifier.weight(1f)) {
            Text(
                "我已明确同意平台收集我的护照信息以及人脸生物识别数据，用于身份核验、反诈以及跨境包车业务；同时我知悉APP会申请位置、相机、相册、麦克风、通讯录、通知设备权限用于出行接驾沟通，我已经完整阅读",
                fontSize = 12.sp,
                lineHeight = 18.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                TextButton(onClick = { onShowLegal("privacy") }, contentPadding = PaddingValues(2.dp)) {
                    Text("《隐私政策》", fontSize = 12.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Medium)
                }
                Text(" ", fontSize = 12.sp)
                TextButton(onClick = { /* KYC page nav */ onShowLegal(null) }, contentPadding = PaddingValues(2.dp)) {
                    Text("《KYC实名认证》", fontSize = 12.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Medium)
                }
                Text("，知晓全部个人数据处理规则。", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f))
            }
        }
    }
}

@Composable
private fun ConfirmRow(label: String, value: String) {
    Row(Modifier.fillMaxWidth()) {
        Text(label, fontSize = 13.sp, color = MaterialTheme.colorScheme.outline, modifier = Modifier.width(100.dp))
        Text(value, fontSize = 13.sp, fontWeight = FontWeight.Medium, modifier = Modifier.weight(1f))
    }
}

/* ========== SEARCHABLE DROPDOWN ========== */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchableDropdown(
    value: String, expanded: Boolean, onExpandedChange: (Boolean) -> Unit,
    label: String, options: List<String>,
    onSelect: (String) -> Unit
) {
    var search by remember { mutableStateOf("") }

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { onExpandedChange(it); if (it) search = "" }) {
        OutlinedTextField(
            value = if (expanded) search else value,
            onValueChange = { if (expanded) search = it },
            readOnly = !expanded,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.fillMaxWidth().menuAnchor(),
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )
        val filtered = if (search.isBlank()) options else options.filter { it.contains(search, ignoreCase = true) }
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { onExpandedChange(false) }) {
            filtered.forEach { opt ->
                DropdownMenuItem(text = { Text(opt) }, onClick = {
                    onSelect(opt)
                    onExpandedChange(false)
                    search = ""
                })
            }
        }
    }
}
