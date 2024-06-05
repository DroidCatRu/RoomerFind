package ru.droidcat.feature.profile.internal.ui.preferenceedit.model

import ru.droidcat.roomerfind.model.network.UserInfoDTO

internal sealed interface Message {

    data object SetLoading : Message

    data class SetMinValue(
        val value: String,
    ) : Message

    data class SetMaxValue(
        val value: String,
    ) : Message

    data class SetMinAge(
        val value: String,
    ) : Message

    data class SetMaxAge(
        val value: String,
    ) : Message

    data class SetProfile(
        val value: UserInfoDTO,
    ) : Message
}
