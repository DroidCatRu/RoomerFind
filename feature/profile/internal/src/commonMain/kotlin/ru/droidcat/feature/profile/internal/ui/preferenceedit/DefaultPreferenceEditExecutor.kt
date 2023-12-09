package ru.droidcat.feature.profile.internal.ui.preferenceedit

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.launch
import ru.droidcat.core.mvi.uiDispatcher
import ru.droidcat.feature.profile.api.model.UserPreference
import ru.droidcat.feature.profile.api.ui.preferencesedit.model.PreferenceState
import ru.droidcat.feature.profile.api.ui.preferencesedit.model.PreferenceState.Loaded
import ru.droidcat.feature.profile.api.usecase.GetPreferenceUseCase
import ru.droidcat.feature.profile.api.usecase.SavePreferenceUseCase
import ru.droidcat.feature.profile.internal.ui.preferenceedit.model.Action
import ru.droidcat.feature.profile.internal.ui.preferenceedit.model.Action.GetUserPreference
import ru.droidcat.feature.profile.internal.ui.preferenceedit.model.Intent
import ru.droidcat.feature.profile.internal.ui.preferenceedit.model.Intent.OnConfirm
import ru.droidcat.feature.profile.internal.ui.preferenceedit.model.Intent.OnMaxAgeChange
import ru.droidcat.feature.profile.internal.ui.preferenceedit.model.Intent.OnMaxValueChange
import ru.droidcat.feature.profile.internal.ui.preferenceedit.model.Intent.OnMinAgeChange
import ru.droidcat.feature.profile.internal.ui.preferenceedit.model.Intent.OnMinValueChange
import ru.droidcat.feature.profile.internal.ui.preferenceedit.model.Label
import ru.droidcat.feature.profile.internal.ui.preferenceedit.model.Label.PreferenceSaved
import ru.droidcat.feature.profile.internal.ui.preferenceedit.model.Message
import ru.droidcat.feature.profile.internal.ui.preferenceedit.model.Message.SetMaxAge
import ru.droidcat.feature.profile.internal.ui.preferenceedit.model.Message.SetMaxValue
import ru.droidcat.feature.profile.internal.ui.preferenceedit.model.Message.SetMinAge
import ru.droidcat.feature.profile.internal.ui.preferenceedit.model.Message.SetMinValue
import ru.droidcat.feature.profile.internal.ui.preferenceedit.model.Message.SetPreference

internal class DefaultPreferenceEditExecutor(
    private val getPreferenceAction: GetPreferenceUseCase,
    private val savePreferenceAction: SavePreferenceUseCase,
) : CoroutineExecutor<Intent, Action, PreferenceState, Message, Label>(uiDispatcher) {

    override fun executeAction(action: Action, getState: () -> PreferenceState) {
        super.executeAction(action, getState)
        when (action) {
            is GetUserPreference -> getUserPreference()
        }
    }

    override fun executeIntent(intent: Intent, getState: () -> PreferenceState) {
        super.executeIntent(intent, getState)
        when (intent) {
            is OnMinValueChange -> onMinValueChange(
                value = intent.value,
            )

            is OnMaxValueChange -> onMaxValueChange(
                value = intent.value,
            )

            is OnMinAgeChange -> onMinAgeChange(
                age = intent.age,
            )

            is OnMaxAgeChange -> onMaxAgeChange(
                age = intent.age,
            )

            is OnConfirm -> onConfirm(
                state = getState()
            )
        }
    }

    private fun getUserPreference() {
        scope.launch {
            val preference = getPreferenceAction().getOrNull() ?: return@launch
            dispatch(SetPreference(preference))
        }
    }

    private fun onMinValueChange(
        value: String,
    ) {
        dispatch(SetMinValue(value))
    }

    private fun onMaxValueChange(
        value: String,
    ) {
        dispatch(SetMaxValue(value))
    }

    private fun onMinAgeChange(
        age: String,
    ) {
        dispatch(SetMinAge(age))
    }

    private fun onMaxAgeChange(
        age: String,
    ) {
        dispatch(SetMaxAge(age))
    }

    private fun onConfirm(
        state: PreferenceState,
    ) {
        if (state !is Loaded) return
        scope.launch {
            val saveResult = savePreferenceAction(
                UserPreference.Defined(
                    minValue = state.minValue,
                    maxValue = state.maxValue,
                    minAge = state.minAge,
                    maxAge = state.maxAge,
                )
            )
            if (saveResult.isSuccess) {
                publish(PreferenceSaved)
            }
        }
    }
}
