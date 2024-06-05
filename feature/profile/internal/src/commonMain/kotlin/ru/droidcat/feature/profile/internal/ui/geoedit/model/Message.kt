package ru.droidcat.feature.profile.internal.ui.geoedit.model

import ru.droidcat.roomerfind.model.network.UserInfoDTO

internal sealed interface Message {

    data class SetLocation(
        val lat: Double,
        val lon: Double,
        val zoom: Double,
        val radius: Double,
    ) : Message

    data class SetProfile(
        val profile: UserInfoDTO,
    ) : Message
}
