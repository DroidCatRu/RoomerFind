package ru.droidcat.feature.profile.api.ui.geoedit.model

import ru.droidcat.roomerfind.model.network.UserInfoDTO

sealed interface GeoEditState {

    data class Loaded(
        val profile: UserInfoDTO,
    ) : GeoEditState

    data object Loading : GeoEditState
}
