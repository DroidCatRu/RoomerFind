package ru.droidcat.feature.auth.internal.ui.register

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.launch
import ru.droidcat.core.mvi.uiDispatcher
import ru.droidcat.feature.auth.api.ui.register.model.RegisterState
import ru.droidcat.feature.auth.api.usecase.RegisterUseCase
import ru.droidcat.feature.auth.internal.ui.register.model.Action
import ru.droidcat.feature.auth.internal.ui.register.model.Intent
import ru.droidcat.feature.auth.internal.ui.register.model.Intent.OnConfirm
import ru.droidcat.feature.auth.internal.ui.register.model.Intent.OnLoginChange
import ru.droidcat.feature.auth.internal.ui.register.model.Intent.OnPasswordChange
import ru.droidcat.feature.auth.internal.ui.register.model.Label
import ru.droidcat.feature.auth.internal.ui.register.model.Message
import ru.droidcat.feature.auth.internal.ui.register.model.Message.SetLogin
import ru.droidcat.feature.auth.internal.ui.register.model.Message.SetPassword

private const val TAG = "DefaultRegisterExecutor"

internal class DefaultRegisterExecutor(
    private val registerAction: RegisterUseCase,
) : CoroutineExecutor<Intent, Action, RegisterState, Message, Label>(uiDispatcher) {

    override fun executeIntent(intent: Intent, getState: () -> RegisterState) {
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
            registerAction(
                login = login,
                password = password,
            )
        }
    }
}
