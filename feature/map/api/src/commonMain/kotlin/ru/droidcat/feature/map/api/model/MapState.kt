package ru.droidcat.feature.map.api.model

data class MapState(
    val center: LatLng = LatLng(0.0, 0.0),
    val zoom: Double = 10.0,
)

data class LatLng(
    val lat: Double,
    val long: Double,
)
