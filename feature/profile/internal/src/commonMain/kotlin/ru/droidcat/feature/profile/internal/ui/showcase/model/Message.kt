package ru.droidcat.feature.profile.internal.ui.showcase.model

import ru.droidcat.roomerfind.model.network.UserInfoDTO

internal sealed interface Message {

    data class SetProfile(val profile: UserInfoDTO) : Message

    data object SetLoading : Message
}
