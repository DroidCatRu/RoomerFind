package ru.droidcat.feature.finders.internal.ui.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceAll
import ru.droidcat.core.mvi.BaseComponent
import ru.droidcat.feature.finders.api.ui.root.FindersComponent
import ru.droidcat.feature.finders.api.ui.root.model.FindersChild.MatchesChild
import ru.droidcat.feature.finders.api.ui.root.model.FindersChild.ProfileChild
import ru.droidcat.feature.finders.api.ui.root.model.FindersChild.SearchChild
import ru.droidcat.feature.finders.internal.ui.matches.createMatchesComponent
import ru.droidcat.feature.finders.internal.ui.root.model.FindersStackConfig
import ru.droidcat.feature.finders.internal.ui.root.model.FindersStackConfig.MatchesConfig
import ru.droidcat.feature.finders.internal.ui.root.model.FindersStackConfig.ProfileConfig
import ru.droidcat.feature.finders.internal.ui.root.model.FindersStackConfig.SearchConfig
import ru.droidcat.feature.finders.internal.ui.search.createSearchComponent

internal class DefaultFindersComponent(
    componentContext: ComponentContext,
    private val onProfile: () -> Unit,
) : FindersComponent, BaseComponent(
    componentContext = componentContext,
) {

    private val navigation = StackNavigation<FindersStackConfig>()

    override val childStack = childStack(
        source = navigation,
        initialConfiguration = SearchConfig,
        handleBackButton = true,
        childFactory = { config, context ->
            when (config) {
                is MatchesConfig -> MatchesChild(
                    component = createMatchesComponent(
                        componentContext = context,
                        onProfile = onProfile,
                        onMatchSelect = ::selectProfile,
                    ),
                )

                is SearchConfig -> SearchChild(
                    component = createSearchComponent(
                        componentContext = context,
                        onSuggestSelect = ::selectProfile,
                    )
                )

                is ProfileConfig -> ProfileChild
            }
        }
    )

    override fun selectMatches() {
        navigation.replaceAll(MatchesConfig)
    }

    override fun selectSearch() {
        navigation.replaceAll(SearchConfig)
    }

    private fun selectProfile() {
        navigation.replaceAll(ProfileConfig)
    }
}

fun createFindersComponent(
    componentContext: ComponentContext,
    onProfile: () -> Unit = {},
): FindersComponent = DefaultFindersComponent(
    componentContext = componentContext,
    onProfile = onProfile,
)
