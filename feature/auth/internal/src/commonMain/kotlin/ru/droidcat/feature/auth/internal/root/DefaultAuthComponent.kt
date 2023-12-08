package ru.droidcat.feature.auth.internal.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import ru.droidcat.core.mvi.BaseComponent
import ru.droidcat.feature.auth.api.root.AuthComponent
import ru.droidcat.feature.auth.api.root.model.AuthChild
import ru.droidcat.feature.auth.api.root.model.AuthChild.LoginChild
import ru.droidcat.feature.auth.api.root.model.AuthChild.RegisterChild
import ru.droidcat.feature.auth.internal.login.createLoginComponent
import ru.droidcat.feature.auth.internal.register.createRegisterComponent
import ru.droidcat.feature.auth.internal.root.model.AuthStackConfig
import ru.droidcat.feature.auth.internal.root.model.AuthStackConfig.LoginConfig
import ru.droidcat.feature.auth.internal.root.model.AuthStackConfig.RegisterConfig

private class DefaultAuthComponent(
    componentContext: ComponentContext,
) : AuthComponent, BaseComponent(
    componentContext = componentContext,
) {

    private val navigation = StackNavigation<AuthStackConfig>()

    override val childStack: Value<ChildStack<*, AuthChild>> = childStack(
        source = navigation,
        initialConfiguration = LoginConfig,
        handleBackButton = true,
        childFactory = { config, context ->
            when (config) {
                is LoginConfig -> LoginChild(
                    component = createLoginComponent(
                        componentContext = context,
                        onRegister = ::navigateRegister,
                    ),
                )

                is RegisterConfig -> RegisterChild(
                    component = createRegisterComponent(
                        componentContext = context,
                        onLogin = ::navigateLogin,
                    ),
                )
            }
        },
    )

    private fun navigateLogin() {
        navigation.replaceAll(LoginConfig)
    }

    private fun navigateRegister() {
        navigation.replaceAll(RegisterConfig)
    }

    private fun navigateBack() {
        navigation.pop()
    }
}

fun createAuthComponent(
    componentContext: ComponentContext,
): AuthComponent = DefaultAuthComponent(
    componentContext = componentContext,
)
