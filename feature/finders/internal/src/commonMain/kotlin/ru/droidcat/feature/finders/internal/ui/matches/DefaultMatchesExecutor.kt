package ru.droidcat.feature.finders.internal.ui.matches

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.launch
import ru.droidcat.core.mvi.uiDispatcher
import ru.droidcat.feature.finders.api.ui.matches.model.MatchesState
import ru.droidcat.feature.finders.api.usecase.GetMatchesUseCase
import ru.droidcat.feature.finders.internal.ui.matches.model.Action
import ru.droidcat.feature.finders.internal.ui.matches.model.Action.GetMatches
import ru.droidcat.feature.finders.internal.ui.matches.model.Intent
import ru.droidcat.feature.finders.internal.ui.matches.model.Label
import ru.droidcat.feature.finders.internal.ui.matches.model.Message
import ru.droidcat.feature.finders.internal.ui.matches.model.Message.SetMatches

internal class DefaultMatchesExecutor(
    private val getMatchesAction: GetMatchesUseCase,
) : CoroutineExecutor<Intent, Action, MatchesState, Message, Label>(
    uiDispatcher
) {

    override fun executeAction(action: Action, getState: () -> MatchesState) {
        super.executeAction(action, getState)
        when (action) {
            is GetMatches -> getMatches()
        }
    }

    private fun getMatches() {
        scope.launch {
            val matches = getMatchesAction().getOrNull() ?: return@launch
            dispatch(SetMatches(matches))
        }
    }
}
