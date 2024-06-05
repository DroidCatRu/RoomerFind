package ru.droidcat.feature.map.internal.model

import ru.droidcat.feature.map.api.model.LatLng

internal sealed interface Message {

    data class SetCameraPosition(
        val center: LatLng,
        val zoom: Double,
    ) : Message
}
