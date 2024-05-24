package ru.droidcat.feature.finders.internal.ui.matches

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.droidcat.core.coroutines.uiDispatcher
import ru.droidcat.feature.finders.api.ui.matches.model.MatchesIntent
import ru.droidcat.feature.finders.api.ui.matches.model.MatchesIntent.OnSelect
import ru.droidcat.feature.finders.api.ui.matches.model.MatchesState
import ru.droidcat.feature.finders.api.usecase.SelectedProfileUseCase
import ru.droidcat.feature.finders.internal.ui.matches.model.Action
import ru.droidcat.feature.finders.internal.ui.matches.model.Action.Init
import ru.droidcat.feature.finders.internal.ui.matches.model.Action.SelectProfile
import ru.droidcat.feature.finders.internal.ui.matches.model.Action.UpdateMatches
import ru.droidcat.feature.finders.internal.ui.matches.model.Label
import ru.droidcat.feature.finders.internal.ui.matches.model.Label.MatchSelected
import ru.droidcat.feature.finders.internal.ui.matches.model.Message
import ru.droidcat.feature.finders.internal.ui.matches.model.Message.SetMatches
import ru.droidcat.feature.finders.internal.usecase.GetMatchesUseCaseImpl

internal class DefaultMatchesExecutor(
    private val getMatchesUseCase: GetMatchesUseCaseImpl,
    private val selectedProfileUseCase: SelectedProfileUseCase,
) : CoroutineExecutor<MatchesIntent, Action, MatchesState, Message, Label>(uiDispatcher) {

    override fun executeAction(action: Action) {
        super.executeAction(action)
        when (action) {
            is Init ->
                getMatchesUseCase.requests
                    .onStart {
                        forward(UpdateMatches)
                    }
                    .onEach {
                        it.getOrNull()?.let { matches ->
                            dispatch(SetMatches(matches))
                        }
                    }
                    .launchIn(scope)

            is UpdateMatches -> scope.launch {
                getMatchesUseCase.updateValue()
            }

            is SelectProfile -> scope.launch {
                selectedProfileUseCase.update(action.id).onSuccess {
                    publish(MatchSelected)
                }
            }
        }
    }

    override fun executeIntent(intent: MatchesIntent) {
        super.executeIntent(intent)
        when (intent) {
            is OnSelect -> forward(SelectProfile(intent.id))
        }
    }
}
