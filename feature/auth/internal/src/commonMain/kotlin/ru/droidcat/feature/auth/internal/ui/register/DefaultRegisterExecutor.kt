package ru.droidcat.feature.auth.internal.ui.register

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.launch
import ru.droidcat.core.coroutines.uiDispatcher
import ru.droidcat.feature.auth.api.ui.register.model.RegisterIntent
import ru.droidcat.feature.auth.api.ui.register.model.RegisterIntent.OnConfirm
import ru.droidcat.feature.auth.api.ui.register.model.RegisterIntent.OnEmailChange
import ru.droidcat.feature.auth.api.ui.register.model.RegisterIntent.OnLogin
import ru.droidcat.feature.auth.api.ui.register.model.RegisterIntent.OnPasswordChange
import ru.droidcat.feature.auth.api.ui.register.model.RegisterState
import ru.droidcat.feature.auth.api.usecase.RegisterByEmailUseCase
import ru.droidcat.feature.auth.internal.ui.register.model.Label
import ru.droidcat.feature.auth.internal.ui.register.model.Message
import ru.droidcat.feature.auth.internal.ui.register.model.Message.SetLogin
import ru.droidcat.feature.auth.internal.ui.register.model.Message.SetPassword

internal class DefaultRegisterExecutor(
    private val registerUseCase: RegisterByEmailUseCase,
) : CoroutineExecutor<RegisterIntent, Nothing, RegisterState, Message, Label>(uiDispatcher) {

    override fun executeIntent(intent: RegisterIntent) {
        super.executeIntent(intent)
        when (intent) {
            is OnEmailChange -> dispatch(SetLogin(intent.value))

            is OnPasswordChange -> dispatch(SetPassword(intent.value))

            is OnConfirm -> scope.launch {
                registerUseCase(
                    email = state().email,
                    password = state().password,
                )
            }

            is OnLogin -> publish(Label.LoginRequest)
        }
    }
}
