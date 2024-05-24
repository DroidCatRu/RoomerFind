package ru.droidcat.feature.finders.api.ui.matches

import kotlinx.coroutines.flow.StateFlow
import ru.droidcat.core.mvi.IntentAcceptor
import ru.droidcat.feature.finders.api.ui.matches.model.MatchesIntent
import ru.droidcat.feature.finders.api.ui.matches.model.MatchesState

interface MatchesComponent : IntentAcceptor<MatchesIntent> {

    val viewState: StateFlow<MatchesState>
}
