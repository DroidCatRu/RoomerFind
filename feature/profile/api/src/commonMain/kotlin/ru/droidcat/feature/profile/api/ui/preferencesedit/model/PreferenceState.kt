package ru.droidcat.feature.profile.api.ui.preferencesedit.model

import ru.droidcat.roomerfind.model.network.UserInfoDTO

sealed interface PreferenceState {
    data class Loaded(
        val profile: UserInfoDTO,
    ) : PreferenceState

    data object Loading : PreferenceState
}
