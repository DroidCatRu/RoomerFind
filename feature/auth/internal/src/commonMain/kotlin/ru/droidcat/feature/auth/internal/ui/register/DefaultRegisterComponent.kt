package ru.droidcat.feature.auth.internal.ui.register

import com.arkivanov.decompose.ComponentContext
import ru.droidcat.core.mvi.BaseComponentWithStore
import ru.droidcat.feature.auth.api.ui.register.RegisterComponent
import ru.droidcat.feature.auth.api.ui.register.model.RegisterIntent
import ru.droidcat.feature.auth.api.ui.register.model.RegisterState
import ru.droidcat.feature.auth.internal.ui.register.model.Label

internal class DefaultRegisterComponent(
    componentContext: ComponentContext,
    private val onLogin: () -> Unit,
) : RegisterComponent, BaseComponentWithStore<RegisterIntent, RegisterState, Label>(
    componentContext = componentContext,
    storeFactory = { get<DefaultRegisterStore>() },
) {

    override fun onLabelReceive(label: Label) {
        super.onLabelReceive(label)
        when (label) {
            is Label.LoginRequest -> onLogin()
        }
    }
}
