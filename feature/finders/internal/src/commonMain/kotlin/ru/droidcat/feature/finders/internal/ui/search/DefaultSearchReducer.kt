package ru.droidcat.feature.finders.internal.ui.search

import com.arkivanov.mvikotlin.core.store.Reducer
import ru.droidcat.feature.finders.api.ui.search.model.SearchState
import ru.droidcat.feature.finders.internal.ui.search.model.Message

internal class DefaultSearchReducer : Reducer<SearchState, Message> {

    override fun SearchState.reduce(msg: Message): SearchState {
        return this
    }
}