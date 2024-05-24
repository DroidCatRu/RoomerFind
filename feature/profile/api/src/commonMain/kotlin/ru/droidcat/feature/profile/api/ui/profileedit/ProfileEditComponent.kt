package ru.droidcat.feature.profile.api.ui.profileedit

import kotlinx.coroutines.flow.StateFlow
import ru.droidcat.feature.profile.api.ui.profileedit.model.ProfileEditState

interface ProfileEditComponent {

    val viewState: StateFlow<ProfileEditState>

    fun onNameChange(name: String)

    fun onAgeChange(age: String)

    fun onDescriptionChange(description: String)

    fun onEmailChange(email: String)

    fun onPhoneChange(phone: String)

    fun onConfirm()

    fun onDismiss()
}
