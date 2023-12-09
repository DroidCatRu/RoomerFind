package ru.droidcat.feature.finders.api.ui.search

import com.arkivanov.decompose.value.Value
import ru.droidcat.feature.finders.api.ui.search.model.SearchState

interface SearchComponent {

    val viewState: Value<SearchState>

    fun onSelect()
}
