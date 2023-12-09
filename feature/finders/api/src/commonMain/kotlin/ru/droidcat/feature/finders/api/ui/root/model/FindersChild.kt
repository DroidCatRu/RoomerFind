package ru.droidcat.feature.finders.api.ui.root.model

import ru.droidcat.feature.finders.api.ui.matches.MatchesComponent
import ru.droidcat.feature.finders.api.ui.search.SearchComponent

sealed interface FindersChild {

    data class MatchesChild(val component: MatchesComponent) : FindersChild

    data object ProfileChild : FindersChild

    data class SearchChild(val component: SearchComponent) : FindersChild
}
