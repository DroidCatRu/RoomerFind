package ru.droidcat.feature.profile.api.ui.geoedit.model

sealed interface GeoEditIntent {

    data object Accept : GeoEditIntent

    data object Dismiss : GeoEditIntent

    data class OnLocationChange(
        val lat: Double,
        val long: Double,
    ) : GeoEditIntent
}
