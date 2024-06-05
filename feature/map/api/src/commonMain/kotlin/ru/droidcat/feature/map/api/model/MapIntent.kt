package ru.droidcat.feature.map.api.model

sealed interface MapIntent {

    data class OnCameraMoveManual(
        val center: LatLng,
        val zoom: Double,
        val distanceToTop: Double,
    ) : MapIntent

    data class OnCameraMove(
        val center: LatLng,
        val zoom: Double,
    ) : MapIntent
}
