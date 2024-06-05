package ru.droidcat.feature.profile.internal.ui.geoedit.model

import ru.droidcat.feature.map.api.model.LatLng

internal sealed interface Label {

    data object DismissRequested : Label

    data class UserGeoChanged(
        val center: LatLng,
        val zoom: Double,
    ) : Label
}
