package ru.droidcat.feature.finders.internal.ui.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import ru.droidcat.core.mvi.BaseComponent
import ru.droidcat.feature.finders.api.ui.root.FindersComponent
import ru.droidcat.feature.finders.api.ui.root.model.FindersChild.FindersTabsChild
import ru.droidcat.feature.finders.api.ui.root.model.FindersChild.ProfileChild
import ru.droidcat.feature.finders.internal.ui.profile.createFinderProfileComponent
import ru.droidcat.feature.finders.internal.ui.root.model.FindersStackConfig
import ru.droidcat.feature.finders.internal.ui.root.model.FindersStackConfig.ProfileConfig
import ru.droidcat.feature.finders.internal.ui.root.model.FindersStackConfig.TabsConfig
import ru.droidcat.feature.finders.internal.ui.tabs.createFindersTabsComponent

internal class DefaultFindersComponent(
    componentContext: ComponentContext,
    private val onProfile: () -> Unit,
) : FindersComponent, BaseComponent(
    componentContext = componentContext,
) {

    private val navigation = StackNavigation<FindersStackConfig>()

    override val childStack = childStack(
        source = navigation,
        serializer = FindersStackConfig.serializer(),
        initialConfiguration = TabsConfig,
        handleBackButton = true,
        childFactory = { config, context ->
            when (config) {
                is TabsConfig -> FindersTabsChild(
                    component = createFindersTabsComponent(
                        componentContext = context,
                        onUserProfile = onProfile,
                        onFinderProfile = ::selectProfile,
                    ),
                )

                is ProfileConfig -> ProfileChild(
                    component = createFinderProfileComponent(
                        componentContext = context,
                        onBack = ::navigateBack,
                    )
                )
            }
        }
    )

    override fun onBackRequest() {
        navigateBack()
    }

    private fun selectProfile() {
        navigation.push(ProfileConfig)
    }

    private fun navigateBack() {
        navigation.pop()
    }
}

fun createFindersComponent(
    componentContext: ComponentContext,
    onProfile: () -> Unit = {},
): FindersComponent = DefaultFindersComponent(
    componentContext = componentContext,
    onProfile = onProfile,
)
