package ru.droidcat.feature.profile.internal.ui.geoedit.model

import ru.droidcat.feature.profile.api.model.Geo

internal sealed interface Message {

    data class SetLocation(
        val lat: Double,
        val lon: Double,
    ) : Message

    data class SetGeo(
        val geo: Geo.Defined,
    ) : Message
}
