package ru.droidcat.feature.profile.api.ui.profileedit

import com.arkivanov.decompose.value.Value
import ru.droidcat.feature.profile.api.ui.profileedit.model.ProfileEditState

interface ProfileEditComponent {

    val viewState: Value<ProfileEditState>

    fun onNameChange(name: String)

    fun onAgeChange(age: String)

    fun onDescriptionChange(description: String)

    fun onEmailChange(email: String)

    fun onPhoneChange(phone: String)

    fun onConfirm()

    fun onDismiss()
}
