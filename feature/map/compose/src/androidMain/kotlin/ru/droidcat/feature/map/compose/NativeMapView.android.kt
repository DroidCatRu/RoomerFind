package ru.droidcat.feature.map.compose

import android.view.View
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapView

actual class NativeMapView(
    val view: View,
    val controller: MapController,
)

class AndroidMapController(
    private val view: MapView,
) : MapController {

    override fun setCameraLocation(
        lat: Double,
        lon: Double,
        zoom: Double,
    ) {
        view.getMapAsync { map ->
            val cameraUpdate = CameraUpdateFactory.newCameraPosition(
                CameraPosition.Builder()
                    .target(
                        LatLng(
                            latitude = lat,
                            longitude = lon,
                        )
                    )
                    .zoom(zoom)
                    .build(),
            )
            map.easeCamera(cameraUpdate)
        }
    }
}
