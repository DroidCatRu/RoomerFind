package ru.droidcat.feature.map.compose

import android.content.Context
import com.mapbox.mapboxsdk.Mapbox
import ru.droidcat.feature.map.api.MapComponent

class AndroidMapProvider(
    private val context: Context,
) : NativeMapProvider {

    override fun createMap(component: MapComponent): NativeMapView {
        Mapbox.getInstance(context)
        val mapView = com.mapbox.mapboxsdk.maps.MapView(context).apply {
            getMapAsync { map ->
                map.setStyle(component.styleUrl)
                map.uiSettings.isLogoEnabled = false
                map.uiSettings.isAttributionEnabled = false

                map.addOnCameraIdleListener {
                    map.cameraPosition.target?.let {
                        component.onCameraMove(
                            lat = it.latitude,
                            lon = it.longitude,
                            zoom = map.cameraPosition.zoom,
                        )
                    }
                }

                map.addOnMapClickListener {
                    component.onCameraMove(
                        lat = it.latitude,
                        lon = it.longitude,
                        zoom = map.cameraPosition.zoom,
                    )
                    true
                }
            }
        }
        return NativeMapView(
            view = mapView,
            controller = AndroidMapController(mapView),
        )
    }
}
