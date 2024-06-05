package ru.droidcat.feature.map.internal.model

import ru.droidcat.feature.map.api.model.LatLng

internal sealed interface Label {

    data class CameraMoved(
        val center: LatLng,
        val zoom: Double,
        val distanceToTop: Double,
    ) : Label
}
