package ru.droidcat.feature.map.compose

import ru.droidcat.feature.map.api.model.LatLng

expect class NativeMapView

interface MapController {

    fun setCameraLocation(
        center: LatLng,
        zoom: Double,
    )
}
