package ru.droidcat.feature.profile.internal.ui.profileedit

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.droidcat.core.coroutines.uiDispatcher
import ru.droidcat.feature.profile.api.ui.profileedit.model.ProfileEditIntent
import ru.droidcat.feature.profile.api.ui.profileedit.model.ProfileEditIntent.OnAgeChange
import ru.droidcat.feature.profile.api.ui.profileedit.model.ProfileEditIntent.OnConfirm
import ru.droidcat.feature.profile.api.ui.profileedit.model.ProfileEditIntent.OnContactsChange
import ru.droidcat.feature.profile.api.ui.profileedit.model.ProfileEditIntent.OnDescChange
import ru.droidcat.feature.profile.api.ui.profileedit.model.ProfileEditIntent.OnDismiss
import ru.droidcat.feature.profile.api.ui.profileedit.model.ProfileEditIntent.OnNameChange
import ru.droidcat.feature.profile.api.ui.profileedit.model.ProfileEditState
import ru.droidcat.feature.profile.api.ui.profileedit.model.ProfileEditState.Loaded
import ru.droidcat.feature.profile.api.usecase.GetProfileUseCase
import ru.droidcat.feature.profile.api.usecase.SaveProfileUseCase
import ru.droidcat.feature.profile.internal.ui.profileedit.model.Action
import ru.droidcat.feature.profile.internal.ui.profileedit.model.Action.GetProfile
import ru.droidcat.feature.profile.internal.ui.profileedit.model.Action.SaveProfile
import ru.droidcat.feature.profile.internal.ui.profileedit.model.Label
import ru.droidcat.feature.profile.internal.ui.profileedit.model.Label.DismissRequested
import ru.droidcat.feature.profile.internal.ui.profileedit.model.Message
import ru.droidcat.feature.profile.internal.ui.profileedit.model.Message.SetAge
import ru.droidcat.feature.profile.internal.ui.profileedit.model.Message.SetContacts
import ru.droidcat.feature.profile.internal.ui.profileedit.model.Message.SetDesc
import ru.droidcat.feature.profile.internal.ui.profileedit.model.Message.SetLoading
import ru.droidcat.feature.profile.internal.ui.profileedit.model.Message.SetName
import ru.droidcat.feature.profile.internal.ui.profileedit.model.Message.SetProfile

internal class DefaultProfileEditExecutor(
    private val getProfileUseCase: GetProfileUseCase,
    private val saveProfileUseCase: SaveProfileUseCase,
) : CoroutineExecutor<ProfileEditIntent, Action, ProfileEditState, Message, Label>(uiDispatcher) {

    override fun executeAction(action: Action) {
        super.executeAction(action)
        when (action) {
            is GetProfile ->
                getProfileUseCase.requests
                    .onStart {
                        dispatch(SetLoading)
                    }
                    .onEach {
                        it.onSuccess {
                            dispatch(SetProfile(it))
                        }.onFailure {
                            publish(DismissRequested)
                        }
                    }
                    .launchIn(scope)

            is SaveProfile -> scope.launch {
                (state() as? Loaded)?.let {
                    saveProfileUseCase.saveProfile(it.profile).onSuccess {
                        publish(DismissRequested)
                    }
                }
            }
        }
    }

    override fun executeIntent(intent: ProfileEditIntent) {
        super.executeIntent(intent)
        when (intent) {
            is OnNameChange -> dispatch(SetName(intent.value))

            is OnAgeChange -> dispatch(SetAge(intent.value))

            is OnDescChange -> dispatch(SetDesc(intent.value))

            is OnContactsChange -> dispatch(SetContacts(intent.value))

            is OnConfirm -> forward(SaveProfile)

            is OnDismiss -> publish(DismissRequested)
        }
    }
}
