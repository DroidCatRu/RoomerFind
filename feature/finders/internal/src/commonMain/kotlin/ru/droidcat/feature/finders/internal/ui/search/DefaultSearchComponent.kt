package ru.droidcat.feature.finders.internal.ui.search

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnResume
import ru.droidcat.core.mvi.BaseComponentWithStore
import ru.droidcat.feature.finders.api.ui.search.SearchComponent
import ru.droidcat.feature.finders.api.ui.search.model.SearchIntent
import ru.droidcat.feature.finders.api.ui.search.model.SearchState
import ru.droidcat.feature.finders.internal.ui.search.model.Label
import ru.droidcat.feature.finders.internal.ui.search.model.Label.FinderSelected

internal class DefaultSearchComponent(
    componentContext: ComponentContext,
    private val onSuggestSelect: () -> Unit,
) : SearchComponent, BaseComponentWithStore<SearchIntent, SearchState, Label>(
    componentContext = componentContext,
    storeFactory = { get<DefaultSearchStore>() },
) {

    init {
        lifecycle.doOnResume { accept(SearchIntent.OnUpdate) }
    }

    override fun onLabelReceive(label: Label) {
        super.onLabelReceive(label)
        when (label) {
            is FinderSelected -> onSuggestSelect()
        }
    }
}

fun createSearchComponent(
    componentContext: ComponentContext,
    onSuggestSelect: () -> Unit = {},
): SearchComponent = DefaultSearchComponent(
    componentContext = componentContext,
    onSuggestSelect = onSuggestSelect,
)
