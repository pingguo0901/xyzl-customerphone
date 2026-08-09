package com.stellarelite.xingyuzhenlv.ui.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stellarelite.xingyuzhenlv.ui.screens.*
import com.stellarelite.xingyuzhenlv.update.UpdateManager

enum class AuthScreen { Entry, Login, Register, ForgotPassword }

@Composable
fun MainScreen() {
    var currentScreen by remember { mutableStateOf(Screen.Home) }

    // ========== 自动版本检测 ==========
    LaunchedEffect(Unit) {
        UpdateManager.checkForUpdate()
    }

    var showBooking by remember { mutableStateOf(false) }
    var showCrossBorder by remember { mutableStateOf(false) }
    var showSingaporeGuide by remember { mutableStateOf(false) }
    var showMalaysiaGuide by remember { mutableStateOf(false) }
    var showNotification by remember { mutableStateOf(false) }
    var showTripDetail by remember { mutableStateOf<TripRecord?>(null) }
    var showLanguage by remember { mutableStateOf(false) }
    var showTheme by remember { mutableStateOf(false) }
    var showVersion by remember { mutableStateOf(false) }
    var showCurrency by remember { mutableStateOf(false) }
    var showWallet by remember { mutableStateOf(false) }
    var showPersonalInfo by remember { mutableStateOf(false) }
    var showPrivacy by remember { mutableStateOf(false) }
    var showAbout by remember { mutableStateOf(false) }
    var showPrivacyPolicy by remember { mutableStateOf(false) }
    var showTermsOfService by remember { mutableStateOf(false) }
    var showRefundPolicy by remember { mutableStateOf(false) }
    var showPaymentFee by remember { mutableStateOf(false) }
    var showKYC by remember { mutableStateOf(false) }
    var showKYCFlow by remember { mutableStateOf(false) }
    var authScreen by remember { mutableStateOf<AuthScreen?>(null) }

    // 认证页面
    when (authScreen) {
        AuthScreen.Entry -> { LoginEntryScreen(onLogin = { authScreen = AuthScreen.Login }, onRegister = { authScreen = AuthScreen.Register }, onBack = { authScreen = null }); return }
        AuthScreen.Login -> { LoginScreen(onBack = { authScreen = AuthScreen.Entry }, onLoginSuccess = { authScreen = null }, onForgotPassword = { authScreen = AuthScreen.ForgotPassword }, onRegister = { authScreen = AuthScreen.Register }); return }
        AuthScreen.Register -> { RegisterScreen(onBack = { authScreen = AuthScreen.Entry }, onRegisterSuccess = { authScreen = null }); return }
        AuthScreen.ForgotPassword -> { ForgotPasswordScreen(onBack = { authScreen = AuthScreen.Login }, onResetSuccess = { authScreen = AuthScreen.Login }); return }
        null -> {}
    }

    if (showBooking) {
        BookingScreen(onBack = { showBooking = false })
        return
    }

    if (showCrossBorder) {
        CrossBorderScreen(onBack = { showCrossBorder = false }, onSingaporeGuide = { showCrossBorder = false; showSingaporeGuide = true }, onMalaysiaGuide = { showCrossBorder = false; showMalaysiaGuide = true })
        return
    }

    if (showSingaporeGuide) {
        SingaporeGuideScreen(onBack = { showSingaporeGuide = false })
        return
    }

    if (showMalaysiaGuide) {
        MalaysiaGuideScreen(onBack = { showMalaysiaGuide = false })
        return
    }

    if (showNotification) {
        NotificationScreen(onBack = { showNotification = false })
        return
    }

    if (showTripDetail != null) {
        val t = showTripDetail!!
        TripDetailScreen(
            trip = TripDetail(
                id = t.id, status = t.status, statusColor = t.statusColor,
                date = t.date, time = t.time, origin = t.origin, destination = t.destination,
                myrAmount = t.myrAmount
            ),
            onBack = { showTripDetail = null }
        )
        return
    }

    if (showLanguage) {
        LanguageScreen(onBack = { showLanguage = false })
        return
    }

    if (showTheme) {
        ThemeScreen(onBack = { showTheme = false })
        return
    }

    if (showVersion) {
        VersionScreen(onBack = { showVersion = false })
        return
    }

    if (showCurrency) {
        CurrencyScreen(onBack = { showCurrency = false })
        return
    }

    if (showPersonalInfo) {
        PersonalInfoScreen(onBack = { showPersonalInfo = false })
        return
    }

    if (showPrivacy) {
        PrivacySecurityScreen(
            onBack = { showPrivacy = false },
            onKYC = { showPrivacy = false; showKYCFlow = true }
        )
        return
    }

    if (showAbout) {
        AboutScreen(onBack = { showAbout = false })
        return
    }

    if (showPrivacyPolicy) {
        PrivacyPolicyScreen(onBack = { showPrivacyPolicy = false })
        return
    }

    if (showTermsOfService) {
        TermsOfServiceScreen(onBack = { showTermsOfService = false })
        return
    }

    if (showRefundPolicy) {
        RefundPolicyScreen(onBack = { showRefundPolicy = false })
        return
    }

    if (showPaymentFee) {
        PaymentFeeScreen(onBack = { showPaymentFee = false })
        return
    }

    if (showKYC) {
        KYCScreen(onBack = { showKYC = false })
        return
    }

    if (showKYCFlow) {
        KYCFlowScreen(onBack = { showKYCFlow = false })
        return
    }

    if (showWallet) {
        WalletScreen(onBack = { showWallet = false })
        return
    }

    // ========== 版本更新弹窗 ==========
    if (UpdateManager.updateAvailable) {
        AlertDialog(
            onDismissRequest = { UpdateManager.updateAvailable = false },
            shape = RoundedCornerShape(20.dp),
            title = {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    Text("🔔 发现新版本", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(4.dp))
                    Text("v${UpdateManager.serverVersion.versionName}", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                }
            },
            text = {
                Column {
                    Text("更新内容：", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                    Spacer(Modifier.height(8.dp))
                    Text(
                        UpdateManager.serverVersion.changelog.replace("\\n", "\n"),
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                        lineHeight = 20.sp
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = { UpdateManager.startDownload() },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("立即更新", fontSize = 15.sp, fontWeight = FontWeight.Medium)
                }
            },
            dismissButton = {
                TextButton(onClick = { UpdateManager.updateAvailable = false }) {
                    Text("稍后提醒", fontSize = 14.sp)
                }
            }
        )
    }

    Scaffold(
        bottomBar = {
            BottomNavBar(
                currentScreen = currentScreen,
                onScreenSelected = { currentScreen = it }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (currentScreen) {
                Screen.Chat -> ChatScreen(onNotification = { showNotification = true })
                Screen.Explore -> ExploreScreen()
                Screen.Home -> HomeScreen(onBookTrip = { showBooking = true }, onCrossBorder = { showCrossBorder = true }, onNotification = { showNotification = true }, onContactSupport = { currentScreen = Screen.Chat })
                Screen.Trips -> TripsScreen(onViewDetail = { showTripDetail = it })
                Screen.Profile -> ProfileScreen(
                    onLoginClick = { authScreen = AuthScreen.Entry },
                    onWalletClick = { showWallet = true },
                    onPersonalInfoClick = { showPersonalInfo = true },
                    onPrivacyClick = { showPrivacy = true },
                    onCurrencyClick = { showCurrency = true },
                    onLanguageClick = { showLanguage = true },
                    onThemeClick = { showTheme = true },
                    onVersionClick = { showVersion = true },
                    onAboutClick = { showAbout = true },
                    onPrivacyPolicyClick = { showPrivacyPolicy = true },
                    onTermsOfServiceClick = { showTermsOfService = true },
                    onRefundPolicyClick = { showRefundPolicy = true },
                    onPaymentFeeClick = { showPaymentFee = true },
                    onKYCClick = { showKYC = true }
                )
            }
        }
    }
}

@Composable
fun BottomNavBar(
    currentScreen: Screen,
    onScreenSelected: (Screen) -> Unit
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background,
        tonalElevation = 8.dp,
        modifier = Modifier.height(130.dp)
    ) {
        Screen.entries.forEach { screen ->
            val selected = currentScreen == screen

            NavigationBarItem(
                selected = selected,
                onClick = { onScreenSelected(screen) },
                icon = {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = if (selected) screen.selectedIcon() else screen.unselectedIcon(),
                            contentDescription = screen.title(),
                            modifier = Modifier.size(24.dp),
                            tint = if (selected) MaterialTheme.colorScheme.primary
                                   else MaterialTheme.colorScheme.outline
                        )
                    }
                },
                label = {
                    Text(
                        text = screen.title(),
                        fontSize = 11.sp,
                        fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                        color = if (selected) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.outline
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    }
}
