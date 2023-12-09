package ru.droidcat.feature.map.internal.model

internal sealed interface Message {

    data class SetCameraPosition(
        val lat: Double,
        val lon: Double,
        val zoom: Double,
    ) : Message
}
