package ru.droidcat.feature.finders.internal.ui.tabs

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceAll
import ru.droidcat.core.mvi.BaseComponent
import ru.droidcat.feature.finders.api.ui.tabs.FindersTabsComponent
import ru.droidcat.feature.finders.api.ui.tabs.model.FindersTabsChild.MatchesChild
import ru.droidcat.feature.finders.api.ui.tabs.model.FindersTabsChild.SearchChild
import ru.droidcat.feature.finders.internal.ui.matches.createMatchesComponent
import ru.droidcat.feature.finders.internal.ui.search.createSearchComponent
import ru.droidcat.feature.finders.internal.ui.tabs.model.FindersTabsConfig
import ru.droidcat.feature.finders.internal.ui.tabs.model.FindersTabsConfig.MatchesConfig
import ru.droidcat.feature.finders.internal.ui.tabs.model.FindersTabsConfig.SearchConfig

internal class DefaultFindersTabsComponent(
    componentContext: ComponentContext,
    private val onUserProfile: () -> Unit,
    private val onFinderProfile: () -> Unit,
) : FindersTabsComponent, BaseComponent(
    componentContext = componentContext,
) {

    private val navigation = StackNavigation<FindersTabsConfig>()

    override val childStack = childStack(
        source = navigation,
        serializer = FindersTabsConfig.serializer(),
        initialConfiguration = SearchConfig,
        handleBackButton = true,
        childFactory = { config, context ->
            when (config) {
                is SearchConfig -> SearchChild(
                    component = createSearchComponent(
                        componentContext = context,
                        onSuggestSelect = onFinderProfile,
                    ),
                )

                is MatchesConfig -> MatchesChild(
                    component = createMatchesComponent(
                        componentContext = context,
                        onMatchSelect = onFinderProfile,
                    ),
                )
            }
        }
    )

    override fun onProfile() {
        onUserProfile()
    }

    override fun navigateToSearch() {
        navigation.replaceAll(SearchConfig)
    }

    override fun navigateToMatches() {
        navigation.replaceAll(MatchesConfig)
    }
}

internal fun createFindersTabsComponent(
    componentContext: ComponentContext,
    onUserProfile: () -> Unit = {},
    onFinderProfile: () -> Unit = {},
): FindersTabsComponent = DefaultFindersTabsComponent(
    componentContext = componentContext,
    onUserProfile = onUserProfile,
    onFinderProfile = onFinderProfile,
)
