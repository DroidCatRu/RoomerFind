package ru.droidcat.feature.map.compose

expect class NativeMapView

interface MapController {

    fun setCameraLocation(
        lat: Double,
        lon: Double,
        zoom: Double,
    )
}
