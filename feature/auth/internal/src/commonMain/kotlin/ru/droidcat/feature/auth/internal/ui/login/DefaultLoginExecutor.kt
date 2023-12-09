package ru.droidcat.feature.auth.internal.ui.login

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.launch
import ru.droidcat.core.mvi.uiDispatcher
import ru.droidcat.feature.auth.api.ui.login.model.LoginState
import ru.droidcat.feature.auth.api.usecase.LoginUseCase
import ru.droidcat.feature.auth.internal.ui.login.model.Action
import ru.droidcat.feature.auth.internal.ui.login.model.Intent
import ru.droidcat.feature.auth.internal.ui.login.model.Intent.OnConfirm
import ru.droidcat.feature.auth.internal.ui.login.model.Intent.OnLoginChange
import ru.droidcat.feature.auth.internal.ui.login.model.Intent.OnPasswordChange
import ru.droidcat.feature.auth.internal.ui.login.model.Label
import ru.droidcat.feature.auth.internal.ui.login.model.Message
import ru.droidcat.feature.auth.internal.ui.login.model.Message.SetLogin
import ru.droidcat.feature.auth.internal.ui.login.model.Message.SetPassword

private const val TAG = "DefaultLoginExecutor"

internal class DefaultLoginExecutor(
    private val loginAction: LoginUseCase,
) : CoroutineExecutor<Intent, Action, LoginState, Message, Label>(uiDispatcher) {

    override fun executeIntent(intent: Intent, getState: () -> LoginState) {
        super.executeIntent(intent, getState)
        when (intent) {
            is OnLoginChange -> onLoginChange(
                login = intent.login,
            )

            is OnPasswordChange -> onPasswordChange(
                password = intent.password,
            )

            is OnConfirm -> onConfirm(
                login = getState().login,
                password = getState().password,
            )
        }
    }

    private fun onLoginChange(
        login: String,
    ) {
        dispatch(SetLogin(login))
    }

    private fun onPasswordChange(
        password: String,
    ) {
        dispatch(SetPassword(password))
    }

    private fun onConfirm(
        login: String,
        password: String,
    ) {
        scope.launch {
            loginAction(
                login = login,
                password = password,
            )
        }
    }
}
