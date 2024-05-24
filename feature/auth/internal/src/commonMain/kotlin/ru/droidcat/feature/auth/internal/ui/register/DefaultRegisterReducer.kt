package ru.droidcat.feature.auth.internal.ui.register

import com.arkivanov.mvikotlin.core.store.Reducer
import ru.droidcat.feature.auth.api.ui.register.model.RegisterState
import ru.droidcat.feature.auth.internal.ui.register.model.Message
import ru.droidcat.feature.auth.internal.ui.register.model.Message.SetLogin
import ru.droidcat.feature.auth.internal.ui.register.model.Message.SetPassword

internal class DefaultRegisterReducer : Reducer<RegisterState, Message> {

    override fun RegisterState.reduce(msg: Message): RegisterState = when (msg) {
        is SetLogin -> this.copy(
            email = msg.value,
        )

        is SetPassword -> this.copy(
            password = msg.value,
        )
    }
}
