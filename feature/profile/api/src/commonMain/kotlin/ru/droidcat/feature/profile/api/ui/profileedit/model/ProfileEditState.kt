package ru.droidcat.feature.profile.api.ui.profileedit.model

import ru.droidcat.roomerfind.model.network.UserInfoDTO

sealed interface ProfileEditState {

    data class Loaded(
        val profile: UserInfoDTO,
    ) : ProfileEditState

    data object Loading : ProfileEditState
}
