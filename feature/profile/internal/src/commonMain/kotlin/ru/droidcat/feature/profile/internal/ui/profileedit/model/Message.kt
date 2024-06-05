package ru.droidcat.feature.profile.internal.ui.profileedit.model

import ru.droidcat.roomerfind.model.network.UserInfoDTO

internal sealed interface Message {

    data class SetName(
        val value: String,
    ) : Message

    data class SetAge(
        val value: String,
    ) : Message

    data class SetDesc(
        val value: String,
    ) : Message

    data class SetContacts(
        val value: String,
    ) : Message

    data class SetProfile(
        val value: UserInfoDTO,
    ) : Message

    data object SetLoading : Message
}
