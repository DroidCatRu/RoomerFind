package ru.droidcat.feature.main.internal

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import ru.droidcat.core.mvi.BaseComponent
import ru.droidcat.feature.finders.internal.ui.root.createFindersComponent
import ru.droidcat.feature.main.internal.model.MainStackConfig
import ru.droidcat.feature.main.internal.model.MainStackConfig.FindersConfig
import ru.droidcat.feature.main.internal.model.MainStackConfig.ProfileConfig
import ru.droidcat.feature.profile.internal.ui.root.createProfileComponent
import ru.droidcat.feature.main.api.MainComponent
import ru.droidcat.feature.main.api.model.MainChild.FindersChild
import ru.droidcat.feature.main.api.model.MainChild.ProfileChild

internal class DefaultMainComponent(
    componentContext: ComponentContext,
) : MainComponent, BaseComponent(
    componentContext = componentContext,
) {

    private val navigation = StackNavigation<MainStackConfig>()

    override val childStack = childStack(
        source = navigation,
        serializer = MainStackConfig.serializer(),
        initialConfiguration = FindersConfig,
        handleBackButton = true,
        childFactory = { config, context ->
            when (config) {
                is ProfileConfig -> ProfileChild(
                    component = createProfileComponent(
                        componentContext = context,
                        onBack = ::onBackRequest,
                    ),
                )

                is FindersConfig -> FindersChild(
                    component = createFindersComponent(
                        componentContext = context,
                        onProfile = { navigation.push(ProfileConfig) },
                    )
                )
            }
        }
    )

    override fun onBackRequest() {
        navigation.pop()
    }
}

fun createMainComponent(
    componentContext: ComponentContext,
): MainComponent = DefaultMainComponent(
    componentContext = componentContext,
)
