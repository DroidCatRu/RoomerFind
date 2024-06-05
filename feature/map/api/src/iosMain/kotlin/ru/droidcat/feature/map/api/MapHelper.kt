package ru.droidcat.feature.map.api

import ru.droidcat.feature.map.api.model.LatLng
import ru.droidcat.feature.map.api.model.MapIntent

class MapHelper(
    private val component: MapComponent,
) {

    fun onMapMoveManual(
        latitude: Double,
        longitude: Double,
        radius: Double,
        zoom: Double,
    ) {
        component.accept(
            MapIntent.OnCameraMoveManual(
                center = LatLng(
                    lat = latitude,
                    long = longitude,
                ),
                distanceToTop = radius,
                zoom = zoom,
            )
        )
    }
}
