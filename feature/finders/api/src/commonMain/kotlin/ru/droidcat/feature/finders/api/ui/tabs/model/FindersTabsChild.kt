package ru.droidcat.feature.finders.api.ui.tabs.model

import ru.droidcat.feature.finders.api.ui.matches.MatchesComponent
import ru.droidcat.feature.finders.api.ui.search.SearchComponent

sealed interface FindersTabsChild {

    data class SearchChild(val component: SearchComponent) : FindersTabsChild

    data class MatchesChild(val component: MatchesComponent) : FindersTabsChild
}
