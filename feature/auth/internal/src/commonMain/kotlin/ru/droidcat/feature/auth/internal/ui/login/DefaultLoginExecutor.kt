package ru.droidcat.feature.auth.internal.ui.login

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.launch
import ru.droidcat.core.coroutines.uiDispatcher
import ru.droidcat.feature.auth.api.ui.login.model.LoginIntent
import ru.droidcat.feature.auth.api.ui.login.model.LoginIntent.OnConfirm
import ru.droidcat.feature.auth.api.ui.login.model.LoginIntent.OnEmailChange
import ru.droidcat.feature.auth.api.ui.login.model.LoginIntent.OnPasswordChange
import ru.droidcat.feature.auth.api.ui.login.model.LoginState
import ru.droidcat.feature.auth.api.usecase.LoginByEmailUseCase
import ru.droidcat.feature.auth.internal.ui.login.model.Label
import ru.droidcat.feature.auth.internal.ui.login.model.Message
import ru.droidcat.feature.auth.internal.ui.login.model.Message.SetLogin
import ru.droidcat.feature.auth.internal.ui.login.model.Message.SetPassword

internal class DefaultLoginExecutor(
    private val loginUseCase: LoginByEmailUseCase,
) : CoroutineExecutor<LoginIntent, Nothing, LoginState, Message, Label>(uiDispatcher) {

    override fun executeIntent(intent: LoginIntent) {
        super.executeIntent(intent)
        when (intent) {
            is OnEmailChange -> dispatch(SetLogin(intent.value))

            is OnPasswordChange -> dispatch(SetPassword(intent.value))

            is OnConfirm -> scope.launch {
                loginUseCase(
                    email = state().email,
                    password = state().password,
                )
            }

            is LoginIntent.OnRegister -> publish(Label.RegisterRequest)
        }
    }
}
