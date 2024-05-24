package ru.droidcat.feature.finders.internal.ui.profile.model

import ru.droidcat.roomerfind.model.network.UserInfoDTO

internal sealed interface Message {

    data class SetProfile(val value: UserInfoDTO) : Message
}
