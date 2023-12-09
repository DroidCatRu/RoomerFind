package ru.droidcat.feature.finders.internal.ui.search

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import ru.droidcat.core.mvi.uiDispatcher
import ru.droidcat.feature.finders.api.ui.search.model.SearchState
import ru.droidcat.feature.finders.internal.ui.search.model.Action
import ru.droidcat.feature.finders.internal.ui.search.model.Intent
import ru.droidcat.feature.finders.internal.ui.search.model.Label
import ru.droidcat.feature.finders.internal.ui.search.model.Message

internal class DefaultSearchExecutor(

) : CoroutineExecutor<Intent, Action, SearchState, Message, Label>(uiDispatcher) {

    override fun executeAction(action: Action, getState: () -> SearchState) {
        super.executeAction(action, getState)
    }

    override fun executeIntent(intent: Intent, getState: () -> SearchState) {
        super.executeIntent(intent, getState)
    }
}