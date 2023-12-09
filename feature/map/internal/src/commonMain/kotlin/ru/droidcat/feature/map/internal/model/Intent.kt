package ru.droidcat.feature.map.internal.model

internal sealed interface Intent {

    data class OnLocationChange(
        val lat: Double,
        val lon: Double,
        val zoom: Double,
    ) : Intent
}
