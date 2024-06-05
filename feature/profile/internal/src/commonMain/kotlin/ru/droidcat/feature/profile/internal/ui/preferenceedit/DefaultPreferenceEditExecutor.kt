package ru.droidcat.feature.profile.internal.ui.preferenceedit

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.droidcat.core.coroutines.uiDispatcher
import ru.droidcat.feature.profile.api.ui.preferencesedit.model.PreferenceEditIntent
import ru.droidcat.feature.profile.api.ui.preferencesedit.model.PreferenceEditIntent.OnConfirm
import ru.droidcat.feature.profile.api.ui.preferencesedit.model.PreferenceEditIntent.OnDismiss
import ru.droidcat.feature.profile.api.ui.preferencesedit.model.PreferenceEditIntent.OnMaxAgeChange
import ru.droidcat.feature.profile.api.ui.preferencesedit.model.PreferenceEditIntent.OnMaxValueChange
import ru.droidcat.feature.profile.api.ui.preferencesedit.model.PreferenceEditIntent.OnMinAgeChange
import ru.droidcat.feature.profile.api.ui.preferencesedit.model.PreferenceEditIntent.OnMinValueChange
import ru.droidcat.feature.profile.api.ui.preferencesedit.model.PreferenceState
import ru.droidcat.feature.profile.api.ui.preferencesedit.model.PreferenceState.Loaded
import ru.droidcat.feature.profile.api.usecase.GetProfileUseCase
import ru.droidcat.feature.profile.api.usecase.SaveProfileUseCase
import ru.droidcat.feature.profile.internal.ui.preferenceedit.model.Action
import ru.droidcat.feature.profile.internal.ui.preferenceedit.model.Action.GetProfile
import ru.droidcat.feature.profile.internal.ui.preferenceedit.model.Action.SaveProfile
import ru.droidcat.feature.profile.internal.ui.preferenceedit.model.Label
import ru.droidcat.feature.profile.internal.ui.preferenceedit.model.Label.DismissRequested
import ru.droidcat.feature.profile.internal.ui.preferenceedit.model.Message
import ru.droidcat.feature.profile.internal.ui.preferenceedit.model.Message.SetLoading
import ru.droidcat.feature.profile.internal.ui.preferenceedit.model.Message.SetMaxAge
import ru.droidcat.feature.profile.internal.ui.preferenceedit.model.Message.SetMaxValue
import ru.droidcat.feature.profile.internal.ui.preferenceedit.model.Message.SetMinAge
import ru.droidcat.feature.profile.internal.ui.preferenceedit.model.Message.SetMinValue
import ru.droidcat.feature.profile.internal.ui.preferenceedit.model.Message.SetProfile

internal class DefaultPreferenceEditExecutor(
    private val getProfileUseCase: GetProfileUseCase,
    private val saveProfileUseCase: SaveProfileUseCase,
) : CoroutineExecutor<PreferenceEditIntent, Action, PreferenceState, Message, Label>(uiDispatcher) {

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

    override fun executeIntent(intent: PreferenceEditIntent) {
        super.executeIntent(intent)
        when (intent) {
            is OnMinValueChange -> dispatch(SetMinValue(intent.value))

            is OnMaxValueChange -> dispatch(SetMaxValue(intent.value))

            is OnMinAgeChange -> dispatch(SetMinAge(intent.value))

            is OnMaxAgeChange -> dispatch(SetMaxAge(intent.value))

            is OnConfirm -> forward(SaveProfile)

            is OnDismiss -> publish(DismissRequested)
        }
    }
}
