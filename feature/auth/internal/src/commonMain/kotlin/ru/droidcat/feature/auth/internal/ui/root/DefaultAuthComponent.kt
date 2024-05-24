package ru.droidcat.feature.auth.internal.ui.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceAll
import ru.droidcat.core.mvi.BaseComponent
import ru.droidcat.feature.auth.api.ui.root.AuthComponent
import ru.droidcat.feature.auth.api.ui.root.model.AuthChild.LoginChild
import ru.droidcat.feature.auth.api.ui.root.model.AuthChild.RegisterChild
import ru.droidcat.feature.auth.internal.ui.login.DefaultLoginComponent
import ru.droidcat.feature.auth.internal.ui.register.DefaultRegisterComponent
import ru.droidcat.feature.auth.internal.ui.root.model.AuthStackConfig
import ru.droidcat.feature.auth.internal.ui.root.model.AuthStackConfig.LoginConfig
import ru.droidcat.feature.auth.internal.ui.root.model.AuthStackConfig.RegisterConfig

internal class DefaultAuthComponent(
    componentContext: ComponentContext,
) : AuthComponent, BaseComponent(
    componentContext = componentContext,
) {

    private val navigation = StackNavigation<AuthStackConfig>()

    override val childStack = childStack(
        source = navigation,
        serializer = AuthStackConfig.serializer(),
        initialConfiguration = LoginConfig,
        handleBackButton = true,
        childFactory = { config, context ->
            when (config) {
                is LoginConfig -> LoginChild(
                    component = DefaultLoginComponent(
                        componentContext = context,
                        onRegister = { navigation.replaceAll(RegisterConfig) },
                    ),
                )

                is RegisterConfig -> RegisterChild(
                    component = DefaultRegisterComponent(
                        componentContext = context,
                        onLogin = { navigation.replaceAll(LoginConfig) },
                    ),
                )
            }
        },
    )
}

fun createAuthComponent(
    componentContext: ComponentContext,
): AuthComponent = DefaultAuthComponent(
    componentContext = componentContext,
)
