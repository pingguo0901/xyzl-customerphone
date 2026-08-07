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

@Composable
fun MainScreen() {
    var currentScreen by remember { mutableStateOf(Screen.Home) }

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
                Screen.Chat -> ChatScreen()
                Screen.Explore -> ExploreScreen()
                Screen.Home -> HomeScreen()
                Screen.Trips -> TripsScreen()
                Screen.Profile -> ProfileScreen()
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
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp,
        modifier = Modifier.height(64.dp)
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
