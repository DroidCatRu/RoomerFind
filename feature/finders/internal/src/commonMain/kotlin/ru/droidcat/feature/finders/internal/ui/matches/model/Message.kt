package ru.droidcat.feature.finders.internal.ui.matches.model

import ru.droidcat.roomerfind.model.network.UserInfoDTO

internal sealed interface Message {

    data class SetMatches(
        val matches: List<UserInfoDTO>,
    ) : Message
}
