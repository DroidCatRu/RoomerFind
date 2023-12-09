package ru.droidcat.feature.root.internal

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.droidcat.core.mvi.uiDispatcher
import ru.droidcat.feature.auth.api.model.UserSession
import ru.droidcat.feature.auth.api.usecase.GetUserSessionUseCase
import ru.droidcat.feature.root.api.model.RootState
import ru.droidcat.feature.root.internal.model.Action
import ru.droidcat.feature.root.internal.model.Intent
import ru.droidcat.feature.root.internal.model.Intent.OnStart
import ru.droidcat.feature.root.internal.model.Label
import ru.droidcat.feature.root.internal.model.Label.UserLoggedIn
import ru.droidcat.feature.root.internal.model.Label.UserLoggedOut
import ru.droidcat.feature.root.internal.model.Message

internal class DefaultRootExecutor(
    private val getUserAction: GetUserSessionUseCase,
) : CoroutineExecutor<Intent, Action, RootState, Message, Label>(uiDispatcher) {

    override fun executeIntent(intent: Intent, getState: () -> RootState) {
        super.executeIntent(intent, getState)
        when (intent) {
            is OnStart -> observeUserSession()
        }
    }

    private fun observeUserSession() {
        getUserAction()
            .onEach { session ->
                when (session) {
                    is UserSession.Defined -> publish(UserLoggedIn)

                    is UserSession.Empty -> publish(UserLoggedOut)

                    else -> {}
                }
            }
            .launchIn(scope)
    }
}
