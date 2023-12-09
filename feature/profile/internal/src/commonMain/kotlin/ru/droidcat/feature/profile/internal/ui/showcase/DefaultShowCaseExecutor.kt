package ru.droidcat.feature.profile.internal.ui.showcase

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.launch
import ru.droidcat.core.mvi.uiDispatcher
import ru.droidcat.feature.auth.api.usecase.LogOutUseCase
import ru.droidcat.feature.profile.api.ui.showcase.model.ProfileShowCaseState
import ru.droidcat.feature.profile.api.usecase.GetContactsUseCase
import ru.droidcat.feature.profile.api.usecase.GetProfileUseCase
import ru.droidcat.feature.profile.internal.ui.showcase.model.Action
import ru.droidcat.feature.profile.internal.ui.showcase.model.Action.GetUserProfile
import ru.droidcat.feature.profile.internal.ui.showcase.model.Intent
import ru.droidcat.feature.profile.internal.ui.showcase.model.Intent.OnLogOut
import ru.droidcat.feature.profile.internal.ui.showcase.model.Label
import ru.droidcat.feature.profile.internal.ui.showcase.model.Message
import ru.droidcat.feature.profile.internal.ui.showcase.model.Message.SetProfile

internal class DefaultShowCaseExecutor(
    private val getProfileAction: GetProfileUseCase,
    private val getContactsAction: GetContactsUseCase,
    private val logOutAction: LogOutUseCase,
) : CoroutineExecutor<Intent, Action, ProfileShowCaseState, Message, Label>(uiDispatcher) {

    override fun executeAction(action: Action, getState: () -> ProfileShowCaseState) {
        super.executeAction(action, getState)
        when (action) {
            is GetUserProfile -> getUserProfile()
        }
    }

    override fun executeIntent(intent: Intent, getState: () -> ProfileShowCaseState) {
        super.executeIntent(intent, getState)
        when (intent) {
            is OnLogOut -> onLogOut()
        }
    }

    private fun getUserProfile() {
        scope.launch {
            val profile = getProfileAction().getOrNull() ?: return@launch
            val contacts = getContactsAction().getOrNull() ?: return@launch
            dispatch(SetProfile(profile, contacts))
        }
    }

    private fun onLogOut() {
        scope.launch {
            logOutAction()
        }
    }
}
