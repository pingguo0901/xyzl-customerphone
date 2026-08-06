package com.stellarelite.xingyuzhenlv.model

data class Trip(
    val id: String = "",
    val dateTime: String = "",
    val origin: String = "",
    val destination: String = "",
    val driverName: String = "",
    val licensePlate: String = "",
    val carModel: String = "",
    val amount: String = "",
    val driverLat: Double = 0.0,
    val driverLng: Double = 0.0,
    val originLat: Double = 0.0,
    val originLng: Double = 0.0,
    val status: TripStatus = TripStatus.NONE
)

enum class TripStatus {
    NONE,
    CONFIRMED,
    DRIVER_ON_THE_WAY,
    ARRIVED
}

data class LatLng(
    val latitude: Double,
    val longitude: Double
)
