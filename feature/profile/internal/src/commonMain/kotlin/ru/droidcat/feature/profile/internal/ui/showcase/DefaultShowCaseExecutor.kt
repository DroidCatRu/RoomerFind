package ru.droidcat.feature.profile.internal.ui.showcase

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.droidcat.core.coroutines.uiDispatcher
import ru.droidcat.feature.auth.api.usecase.LogOutUseCase
import ru.droidcat.feature.profile.api.ui.showcase.model.ProfileShowCaseIntent
import ru.droidcat.feature.profile.api.ui.showcase.model.ProfileShowCaseIntent.OnBack
import ru.droidcat.feature.profile.api.ui.showcase.model.ProfileShowCaseIntent.OnEditGeo
import ru.droidcat.feature.profile.api.ui.showcase.model.ProfileShowCaseIntent.OnEditPreferences
import ru.droidcat.feature.profile.api.ui.showcase.model.ProfileShowCaseIntent.OnEditProfile
import ru.droidcat.feature.profile.api.ui.showcase.model.ProfileShowCaseIntent.OnLogOut
import ru.droidcat.feature.profile.api.ui.showcase.model.ProfileShowCaseState
import ru.droidcat.feature.profile.api.usecase.GetProfileUseCase
import ru.droidcat.feature.profile.internal.ui.showcase.model.Action
import ru.droidcat.feature.profile.internal.ui.showcase.model.Action.Init
import ru.droidcat.feature.profile.internal.ui.showcase.model.Action.LogOut
import ru.droidcat.feature.profile.internal.ui.showcase.model.Action.Update
import ru.droidcat.feature.profile.internal.ui.showcase.model.Label
import ru.droidcat.feature.profile.internal.ui.showcase.model.Label.BackRequested
import ru.droidcat.feature.profile.internal.ui.showcase.model.Label.EditGeoRequested
import ru.droidcat.feature.profile.internal.ui.showcase.model.Label.EditPreferencesRequested
import ru.droidcat.feature.profile.internal.ui.showcase.model.Label.EditProfileRequested
import ru.droidcat.feature.profile.internal.ui.showcase.model.Message
import ru.droidcat.feature.profile.internal.ui.showcase.model.Message.SetLoading

internal class DefaultShowCaseExecutor(
    private val getProfileUseCase: GetProfileUseCase,
    private val logOutUseCase: LogOutUseCase,
) : CoroutineExecutor<ProfileShowCaseIntent, Action, ProfileShowCaseState, Message, Label>(uiDispatcher) {

    override fun executeAction(action: Action) {
        super.executeAction(action)
        when (action) {
            is Init ->
                getProfileUseCase.requests
                    .onStart {
                        forward(Update)
                    }
                    .onEach {
                        it.onSuccess {
                            dispatch(Message.SetProfile(it))
                        }.onFailure {
                            publish(BackRequested)
                        }
                    }
                    .launchIn(scope)

            is Update -> scope.launch {
                dispatch(SetLoading)
                getProfileUseCase.updateValue().onFailure {
                    publish(BackRequested)
                }
            }

            is LogOut -> scope.launch {
                logOutUseCase.invoke()
            }
        }
    }

    override fun executeIntent(intent: ProfileShowCaseIntent) {
        super.executeIntent(intent)
        when (intent) {
            is OnEditProfile -> publish(EditProfileRequested)
            is OnEditPreferences -> publish(EditPreferencesRequested)
            is OnEditGeo -> publish(EditGeoRequested)
            is OnBack -> publish(BackRequested)
            is OnLogOut -> forward(LogOut)
        }
    }
}
