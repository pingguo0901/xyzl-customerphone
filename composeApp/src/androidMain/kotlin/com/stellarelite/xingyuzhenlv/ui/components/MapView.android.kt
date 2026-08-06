package com.stellarelite.xingyuzhenlv.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.gms.maps.GoogleMap
import android.graphics.Color

@Composable
actual fun MapView(
    userLat: Double,
    userLng: Double,
    originLat: Double,
    originLng: Double,
    driverLat: Double,
    driverLng: Double
) {
    val points = listOf(
        LatLng(driverLat, driverLng),
        LatLng(originLat, originLng)
    )

    AndroidView(
        factory = { context ->
            val mv = MapView(context)
            mv.onCreate(null)
            mv.getMapAsync { map ->
                map.uiSettings.isZoomControlsEnabled = true
                map.mapType = GoogleMap.MAP_TYPE_NORMAL

                // User location marker
                map.addMarker(
                    MarkerOptions()
                        .position(LatLng(userLat, userLng))
                        .title("您的当前位置")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                )

                // Origin marker
                map.addMarker(
                    MarkerOptions()
                        .position(LatLng(originLat, originLng))
                        .title("起点")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                )

                // Driver marker
                map.addMarker(
                    MarkerOptions()
                        .position(LatLng(driverLat, driverLng))
                        .title("司机位置")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                )

                // Route line between driver and origin
                map.addPolyline(
                    PolylineOptions()
                        .addAll(points)
                        .width(8f)
                        .color(Color.rgb(26, 115, 232))
                )

                // Fit bounds
                val builder = LatLngBounds.builder()
                builder.include(LatLng(userLat, userLng))
                builder.include(LatLng(originLat, originLng))
                builder.include(LatLng(driverLat, driverLng))
                val bounds = builder.build()
                val padding = 80
                map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding))
            }
            mv
        },
        modifier = Modifier.fillMaxSize(),
        update = { mapView ->
            mapView.getMapAsync { map ->
                map.clear()

                // Re-add markers
                map.addMarker(
                    MarkerOptions()
                        .position(LatLng(userLat, userLng))
                        .title("您的当前位置")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                )
                map.addMarker(
                    MarkerOptions()
                        .position(LatLng(originLat, originLng))
                        .title("起点")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                )
                map.addMarker(
                    MarkerOptions()
                        .position(LatLng(driverLat, driverLng))
                        .title("司机位置")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                )
                map.addPolyline(
                    PolylineOptions()
                        .add(LatLng(driverLat, driverLng), LatLng(originLat, originLng))
                        .width(8f)
                        .color(Color.rgb(26, 115, 232))
                )
            }
        }
    )
}
