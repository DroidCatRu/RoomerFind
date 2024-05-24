package ru.droidcat.feature.root.internal

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.droidcat.core.coroutines.uiDispatcher
import ru.droidcat.feature.auth.api.usecase.TokenUseCase
import ru.droidcat.feature.root.api.model.RootState
import ru.droidcat.feature.root.internal.model.Action
import ru.droidcat.feature.root.internal.model.Intent
import ru.droidcat.feature.root.internal.model.Intent.OnStart
import ru.droidcat.feature.root.internal.model.Label
import ru.droidcat.feature.root.internal.model.Label.UserLoggedIn
import ru.droidcat.feature.root.internal.model.Label.UserLoggedOut
import ru.droidcat.feature.root.internal.model.Message

internal class DefaultRootExecutor(
    private val tokenUseCase: TokenUseCase,
) : CoroutineExecutor<Intent, Action, RootState, Message, Label>(uiDispatcher) {

    override fun executeIntent(intent: Intent) {
        super.executeIntent(intent)
        when (intent) {
            is OnStart -> tokenUseCase.requests.onEach {
                it.onSuccess {
                    publish(UserLoggedIn)
                }.onFailure {
                    publish(UserLoggedOut)
                }
            }.launchIn(scope)
        }
    }
}
