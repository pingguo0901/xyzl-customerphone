package com.stellarelite.xingyuzhenlv.ui.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stellarelite.xingyuzhenlv.ui.screens.*

enum class AuthScreen { Entry, Login, Register, ForgotPassword }

@Composable
fun MainScreen() {
    var currentScreen by remember { mutableStateOf(Screen.Home) }
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
    var authScreen by remember { mutableStateOf<AuthScreen?>(null) }

    // 认证页面
    when (authScreen) {
        AuthScreen.Entry -> { LoginEntryScreen(onLogin = { authScreen = AuthScreen.Login }, onRegister = { authScreen = AuthScreen.Register }, onBack = { authScreen = null }); return }
        AuthScreen.Login -> { LoginScreen(onBack = { authScreen = AuthScreen.Entry }, onLoginSuccess = { authScreen = null }, onForgotPassword = { authScreen = AuthScreen.ForgotPassword }); return }
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
                price = t.price
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
                Screen.Home -> HomeScreen(onBookTrip = { showBooking = true }, onCrossBorder = { showCrossBorder = true }, onNotification = { showNotification = true })
                Screen.Trips -> TripsScreen(onViewDetail = { showTripDetail = it })
                Screen.Profile -> ProfileScreen(
                    onLoginClick = { authScreen = AuthScreen.Entry },
                    onCurrencyClick = { showCurrency = true },
                    onLanguageClick = { showLanguage = true },
                    onThemeClick = { showTheme = true },
                    onVersionClick = { showVersion = true }
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
