package ru.droidcat.feature.profile.internal.ui.profileedit

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.launch
import ru.droidcat.core.mvi.uiDispatcher
import ru.droidcat.feature.profile.api.model.Contacts
import ru.droidcat.feature.profile.api.model.UserProfile
import ru.droidcat.feature.profile.api.ui.profileedit.model.ProfileEditState
import ru.droidcat.feature.profile.api.usecase.GetContactsUseCase
import ru.droidcat.feature.profile.api.usecase.GetProfileUseCase
import ru.droidcat.feature.profile.api.usecase.SaveContactsUseCase
import ru.droidcat.feature.profile.api.usecase.SaveProfileUseCase
import ru.droidcat.feature.profile.internal.ui.profileedit.model.Action
import ru.droidcat.feature.profile.internal.ui.profileedit.model.Action.GetUserProfile
import ru.droidcat.feature.profile.internal.ui.profileedit.model.Intent
import ru.droidcat.feature.profile.internal.ui.profileedit.model.Intent.OnAgeChange
import ru.droidcat.feature.profile.internal.ui.profileedit.model.Intent.OnConfirm
import ru.droidcat.feature.profile.internal.ui.profileedit.model.Intent.OnDescChange
import ru.droidcat.feature.profile.internal.ui.profileedit.model.Intent.OnEmailChange
import ru.droidcat.feature.profile.internal.ui.profileedit.model.Intent.OnNameChange
import ru.droidcat.feature.profile.internal.ui.profileedit.model.Intent.OnPhoneChange
import ru.droidcat.feature.profile.internal.ui.profileedit.model.Label
import ru.droidcat.feature.profile.internal.ui.profileedit.model.Label.ProfileSaved
import ru.droidcat.feature.profile.internal.ui.profileedit.model.Message
import ru.droidcat.feature.profile.internal.ui.profileedit.model.Message.SetAge
import ru.droidcat.feature.profile.internal.ui.profileedit.model.Message.SetDesc
import ru.droidcat.feature.profile.internal.ui.profileedit.model.Message.SetEmail
import ru.droidcat.feature.profile.internal.ui.profileedit.model.Message.SetName
import ru.droidcat.feature.profile.internal.ui.profileedit.model.Message.SetPhone
import ru.droidcat.feature.profile.internal.ui.profileedit.model.Message.SetProfile

internal class DefaultProfileEditExecutor(
    private val getProfileAction: GetProfileUseCase,
    private val getContactsAction: GetContactsUseCase,
    private val saveProfileAction: SaveProfileUseCase,
    private val saveContactsAction: SaveContactsUseCase
) : CoroutineExecutor<Intent, Action, ProfileEditState, Message, Label>(uiDispatcher) {

    override fun executeAction(action: Action, getState: () -> ProfileEditState) {
        super.executeAction(action, getState)
        when (action) {
            is GetUserProfile -> getUserProfile()
        }
    }

    override fun executeIntent(intent: Intent, getState: () -> ProfileEditState) {
        super.executeIntent(intent, getState)
        when (intent) {
            is OnNameChange -> onNameChange(
                name = intent.name,
            )

            is OnAgeChange -> onAgeChange(
                age = intent.age,
            )

            is OnDescChange -> onDescChange(
                desc = intent.desc,
            )

            is OnEmailChange -> onEmailChange(
                email = intent.email,
            )

            is OnPhoneChange -> onPhoneChange(
                phone = intent.phone,
            )

            is OnConfirm -> onConfirm(
                state = getState(),
            )
        }
    }

    private fun getUserProfile() {
        scope.launch {
            val profile = getProfileAction().getOrNull() ?: return@launch
            val contacts = getContactsAction().getOrNull() ?: return@launch
            dispatch(SetProfile(profile, contacts))
        }
    }

    private fun onNameChange(
        name: String,
    ) {
        dispatch(SetName(name))
    }

    private fun onAgeChange(
        age: String,
    ) {
        dispatch(SetAge(age))
    }

    private fun onDescChange(
        desc: String,
    ) {
        dispatch(SetDesc(desc))
    }

    private fun onEmailChange(
        email: String,
    ) {
        dispatch(SetEmail(email))
    }

    private fun onPhoneChange(
        phone: String,
    ) {
        dispatch(SetPhone(phone))
    }

    private fun onConfirm(
        state: ProfileEditState,
    ) {
        if (state !is ProfileEditState.Loaded) return
        scope.launch {
            val profileResult = saveProfileAction(
                UserProfile.Defined(
                    name = state.name,
                    age = state.age,
                    description = state.description,
                )
            )
            val contactsResult = saveContactsAction(
                Contacts.Defined(
                    email = state.email,
                    phone = state.phone,
                )
            )
            if (profileResult.isSuccess && contactsResult.isSuccess) {
                publish(ProfileSaved)
            }
        }
    }
}
