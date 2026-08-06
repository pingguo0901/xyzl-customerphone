package com.stellarelite.xingyuzhenlv.ui.components

import androidx.compose.runtime.Composable

@Composable
expect fun MapView(
    userLat: Double,
    userLng: Double,
    originLat: Double,
    originLng: Double,
    driverLat: Double,
    driverLng: Double
)
