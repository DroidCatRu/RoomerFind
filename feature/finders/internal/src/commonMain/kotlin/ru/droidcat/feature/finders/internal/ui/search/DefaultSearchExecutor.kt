package ru.droidcat.feature.finders.internal.ui.search

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.droidcat.core.coroutines.uiDispatcher
import ru.droidcat.feature.finders.api.model.Reaction
import ru.droidcat.feature.finders.api.ui.search.model.SearchIntent
import ru.droidcat.feature.finders.api.ui.search.model.SearchIntent.OnDislike
import ru.droidcat.feature.finders.api.ui.search.model.SearchIntent.OnFinderProfileTap
import ru.droidcat.feature.finders.api.ui.search.model.SearchIntent.OnLike
import ru.droidcat.feature.finders.api.ui.search.model.SearchIntent.OnUpdate
import ru.droidcat.feature.finders.api.ui.search.model.SearchState
import ru.droidcat.feature.finders.api.usecase.GetFinderUseCase
import ru.droidcat.feature.finders.api.usecase.SelectedProfileUseCase
import ru.droidcat.feature.finders.api.usecase.SendReactionUseCase
import ru.droidcat.feature.finders.internal.ui.search.model.Action
import ru.droidcat.feature.finders.internal.ui.search.model.Action.GetNext
import ru.droidcat.feature.finders.internal.ui.search.model.Action.Init
import ru.droidcat.feature.finders.internal.ui.search.model.Action.Select
import ru.droidcat.feature.finders.internal.ui.search.model.Action.SetReaction
import ru.droidcat.feature.finders.internal.ui.search.model.Label
import ru.droidcat.feature.finders.internal.ui.search.model.Label.FinderSelected
import ru.droidcat.feature.finders.internal.ui.search.model.Message
import ru.droidcat.feature.finders.internal.ui.search.model.Message.SetLoading
import ru.droidcat.feature.finders.internal.ui.search.model.Message.SetNoProfile
import ru.droidcat.feature.finders.internal.ui.search.model.Message.SetProfile

internal class DefaultSearchExecutor(
    private val getFinderUseCase: GetFinderUseCase,
    private val sendReactionUseCase: SendReactionUseCase,
    private val selectedProfileUseCase: SelectedProfileUseCase,
) : CoroutineExecutor<SearchIntent, Action, SearchState, Message, Label>(uiDispatcher) {

    override fun executeAction(action: Action) {
        super.executeAction(action)
        when (action) {
            is Init ->
                getFinderUseCase.requests
                    .onStart {
                        forward(GetNext)
                    }
                    .onEach {
                        it.onSuccess {
                            dispatch(SetProfile(it))
                        }.onFailure {
                            dispatch(SetNoProfile)
                        }
                    }
                    .launchIn(scope)

            is GetNext -> scope.launch {
                dispatch(SetLoading)
                getFinderUseCase.updateValue().onFailure {
                    dispatch(SetNoProfile)
                }
            }

            is Select -> scope.launch {
                selectedProfileUseCase.update(action.id).onSuccess {
                    publish(FinderSelected)
                }
            }

            is SetReaction -> scope.launch {
                sendReactionUseCase.sendReaction(action.reaction).onSuccess {
                    forward(GetNext)
                }
            }
        }
    }

    override fun executeIntent(intent: SearchIntent) {
        super.executeIntent(intent)
        when (intent) {
            is OnFinderProfileTap -> forward(Select(intent.id))

            is OnLike -> forward(SetReaction(Reaction.Like))

            is OnDislike -> forward(SetReaction(Reaction.Dislike))

            is OnUpdate -> forward(GetNext)
        }
    }
}
