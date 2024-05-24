package ru.droidcat.feature.finders.api.ui.search

import kotlinx.coroutines.flow.StateFlow
import ru.droidcat.core.mvi.IntentAcceptor
import ru.droidcat.feature.finders.api.ui.search.model.SearchIntent
import ru.droidcat.feature.finders.api.ui.search.model.SearchState

interface SearchComponent : IntentAcceptor<SearchIntent> {

    val viewState: StateFlow<SearchState>
}
