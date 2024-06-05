package ru.droidcat.feature.map.compose

import android.view.View
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapView
import ru.droidcat.feature.map.api.model.LatLng as MapLatLng

actual class NativeMapView(
    val view: View,
    val controller: MapController,
)

class AndroidMapController(
    private val view: MapView,
) : MapController {

    override fun setCameraLocation(
        center: MapLatLng,
        zoom: Double,
    ) {
        view.getMapAsync { map ->
            val cameraUpdate = CameraUpdateFactory.newCameraPosition(
                CameraPosition.Builder()
                    .target(
                        center.nativeLatLng,
                    )
                    .zoom(zoom)
                    .build(),
            )
            map.easeCamera(cameraUpdate)
        }
    }
}

val MapLatLng.nativeLatLng: LatLng get() = LatLng(
    latitude = lat,
    longitude = long,
)
