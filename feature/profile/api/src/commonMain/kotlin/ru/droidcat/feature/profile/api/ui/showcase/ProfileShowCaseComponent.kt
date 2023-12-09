package ru.droidcat.feature.profile.api.ui.showcase

import com.arkivanov.decompose.value.Value
import ru.droidcat.feature.profile.api.ui.showcase.model.ProfileShowCaseState

interface ProfileShowCaseComponent {

    val viewState: Value<ProfileShowCaseState>

    fun onEditProfileRequest()

    fun onEditPreferenceRequest()

    fun onGeoEditRequest()

    fun onBackRequest()

    fun onLogOutRequest()
}
