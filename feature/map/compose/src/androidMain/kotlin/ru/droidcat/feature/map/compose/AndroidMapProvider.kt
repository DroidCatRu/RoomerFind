package ru.droidcat.feature.map.compose

import android.content.Context
import android.graphics.PointF
import com.mapbox.mapboxsdk.Mapbox
import ru.droidcat.feature.map.api.MapComponent
import ru.droidcat.feature.map.api.model.LatLng
import ru.droidcat.feature.map.api.model.MapIntent

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
                    val topLatLng = map.projection.fromScreenLocation(
                        PointF(0f, map.width / 2)
                    )
                    map.cameraPosition.target?.let {
                        component.accept(
                            MapIntent.OnCameraMoveManual(
                                center = LatLng(
                                    lat = it.latitude,
                                    long = it.longitude,
                                ),
                                distanceToTop = it.distanceTo(topLatLng) / 1000 * 0.8,
                                zoom = map.cameraPosition.zoom,
                            )
                        )
                    }
                }
            }
        }
        return NativeMapView(
            view = mapView,
            controller = AndroidMapController(mapView),
        )
    }
}
