package ru.droidcat.feature.finders.internal.ui.search.model

import ru.droidcat.roomerfind.model.network.UserInfoDTO

internal sealed interface Message {

    data class SetProfile(val value: UserInfoDTO) : Message

    data object SetLoading : Message

    data object SetNoProfile : Message
}
