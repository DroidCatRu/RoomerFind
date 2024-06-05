package ru.droidcat.feature.root.internal

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.essenty.lifecycle.doOnStart
import ru.droidcat.core.mvi.BaseComponentWithStore
import ru.droidcat.feature.auth.internal.ui.root.createAuthComponent
import ru.droidcat.feature.main.internal.createMainComponent
import ru.droidcat.feature.root.api.RootComponent
import ru.droidcat.feature.root.api.model.RootChild
import ru.droidcat.feature.root.api.model.RootChild.AuthChild
import ru.droidcat.feature.root.api.model.RootChild.SplashChild
import ru.droidcat.feature.root.api.model.RootState
import ru.droidcat.feature.root.internal.model.Intent
import ru.droidcat.feature.root.internal.model.Intent.OnStart
import ru.droidcat.feature.root.internal.model.Label
import ru.droidcat.feature.root.internal.model.Label.UserLoggedIn
import ru.droidcat.feature.root.internal.model.Label.UserLoggedOut
import ru.droidcat.feature.root.internal.model.RootStackConfig
import ru.droidcat.feature.root.internal.model.RootStackConfig.AuthConfig
import ru.droidcat.feature.root.internal.model.RootStackConfig.MainConfig
import ru.droidcat.feature.root.internal.model.RootStackConfig.SplashConfig

internal class DefaultRootComponent(
    componentContext: ComponentContext,
) : RootComponent, BaseComponentWithStore<Intent, RootState, Label>(
    componentContext = componentContext,
    storeFactory = { get<DefaultRootStore>() }
) {

    private val navigation = StackNavigation<RootStackConfig>()

    override val childStack = childStack(
        source = navigation,
        serializer = RootStackConfig.serializer(),
        initialConfiguration = SplashConfig,
        handleBackButton = true,
        childFactory = { config, context ->
            when (config) {
                is SplashConfig -> SplashChild

                is AuthConfig -> AuthChild(
                    component = createAuthComponent(
                        componentContext = context,
                    ),
                )

                is MainConfig -> RootChild.MainChild(
                    component = createMainComponent(
                        componentContext = context,
                    ),
                )
            }
        }
    )

    init {
        lifecycle.doOnStart(
            isOneTime = true,
        ) {
            accept(OnStart)
        }
    }

    override fun onLabelReceive(label: Label) {
        super.onLabelReceive(label)
        when (label) {
            is UserLoggedIn -> navigation.replaceAll(MainConfig)

            is UserLoggedOut -> navigation.replaceAll(AuthConfig)
        }
    }
}

fun createRootComponent(
    componentContext: ComponentContext,
): RootComponent = DefaultRootComponent(
    componentContext = componentContext,
)
