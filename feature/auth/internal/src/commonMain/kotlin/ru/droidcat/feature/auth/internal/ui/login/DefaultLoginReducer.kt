package ru.droidcat.feature.auth.internal.ui.login

import com.arkivanov.mvikotlin.core.store.Reducer
import ru.droidcat.feature.auth.api.ui.login.model.LoginState
import ru.droidcat.feature.auth.internal.ui.login.model.Message
import ru.droidcat.feature.auth.internal.ui.login.model.Message.SetLogin
import ru.droidcat.feature.auth.internal.ui.login.model.Message.SetPassword

internal class DefaultLoginReducer : Reducer<LoginState, Message> {

    override fun LoginState.reduce(msg: Message): LoginState {
        return when (msg) {
            is SetLogin -> this.copy(
                login = msg.login,
            )

            is SetPassword -> this.copy(
                password = msg.password,
            )
        }
    }
}
