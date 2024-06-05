package ru.droidcat.feature.profile.api.ui.geoedit.model

import ru.droidcat.feature.map.api.model.LatLng

sealed interface GeoEditIntent {

    data object OnConfirm : GeoEditIntent

    data object OnDismiss : GeoEditIntent

    data class OnLocationChange(
        val center: LatLng,
        val zoom: Double,
        val radius: Double,
    ) : GeoEditIntent
}
