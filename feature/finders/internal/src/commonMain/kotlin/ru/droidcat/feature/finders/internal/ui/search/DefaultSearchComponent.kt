package ru.droidcat.feature.finders.internal.ui.search

import com.arkivanov.decompose.ComponentContext
import ru.droidcat.core.mvi.BaseComponentWithStore
import ru.droidcat.feature.finders.api.ui.search.SearchComponent
import ru.droidcat.feature.finders.api.ui.search.model.SearchState
import ru.droidcat.feature.finders.internal.ui.search.model.Intent
import ru.droidcat.feature.finders.internal.ui.search.model.Label

internal class DefaultSearchComponent(
    componentContext: ComponentContext,
    private val onSuggestSelect: () -> Unit,
) : SearchComponent, BaseComponentWithStore<Intent, SearchState, Label>(
    componentContext = componentContext,
    storeFactory = { get<DefaultSearchStore>() },
) {

    override fun onSelect() {
        onSuggestSelect()
    }
}

fun createSearchComponent(
    componentContext: ComponentContext,
    onSuggestSelect: () -> Unit = {},
): SearchComponent = DefaultSearchComponent(
    componentContext = componentContext,
    onSuggestSelect = onSuggestSelect,
)
