package ru.droidcat.feature.map.api.model

data class MapState(
    val markerPosition: MarkerPosition = MarkerPosition(),
)

data class MarkerPosition(
    val lat: Double = 0.0,
    val lon: Double = 0.0,
    val zoom: Double = 1.0,
)
