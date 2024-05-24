package ru.droidcat.feature.profile.api.ui.geoedit.model

sealed interface GeoEditState {

    data class Loaded(
        val lat: Double = 0.0,
        val lon: Double = 0.0,
        val radius: Double = 10.0,
    ) : GeoEditState

    data object Loading : GeoEditState
}
