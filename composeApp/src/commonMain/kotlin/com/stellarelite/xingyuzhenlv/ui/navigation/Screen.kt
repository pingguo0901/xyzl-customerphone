package com.stellarelite.xingyuzhenlv.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.stellarelite.xingyuzhenlv.i18n.LanguageManager
import com.stellarelite.xingyuzhenlv.i18n.t

enum class Screen {
    Chat, Explore, Home, Trips, Profile;

    fun title(): String = when (this) {
        Chat -> t("tab_chat")
        Explore -> t("tab_explore")
        Home -> t("tab_home")
        Trips -> t("tab_trips")
        Profile -> t("tab_profile")
    }

    fun selectedIcon(): ImageVector = when (this) {
        Chat -> Icons.Filled.ChatBubble
        Explore -> Icons.Filled.Explore
        Home -> Icons.Filled.Home
        Trips -> Icons.Filled.DirectionsCar
        Profile -> Icons.Filled.Person
    }

    fun unselectedIcon(): ImageVector = when (this) {
        Chat -> Icons.Outlined.ChatBubbleOutline
        Explore -> Icons.Outlined.Explore
        Home -> Icons.Outlined.Home
        Trips -> Icons.Outlined.DirectionsCar
        Profile -> Icons.Outlined.Person
    }
}
