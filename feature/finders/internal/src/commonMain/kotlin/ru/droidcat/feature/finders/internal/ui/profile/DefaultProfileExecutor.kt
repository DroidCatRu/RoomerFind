package ru.droidcat.feature.finders.internal.ui.profile

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.droidcat.core.coroutines.uiDispatcher
import ru.droidcat.feature.finders.api.model.Reaction
import ru.droidcat.feature.finders.api.ui.profile.model.FinderProfileIntent
import ru.droidcat.feature.finders.api.ui.profile.model.FinderProfileIntent.OnBackClick
import ru.droidcat.feature.finders.api.ui.profile.model.FinderProfileIntent.OnDislike
import ru.droidcat.feature.finders.api.ui.profile.model.FinderProfileIntent.OnLike
import ru.droidcat.feature.finders.api.ui.profile.model.ProfileState
import ru.droidcat.feature.finders.api.usecase.SelectedProfileUseCase
import ru.droidcat.feature.finders.api.usecase.SendReactionUseCase
import ru.droidcat.feature.finders.internal.ui.profile.model.Action
import ru.droidcat.feature.finders.internal.ui.profile.model.Action.Init
import ru.droidcat.feature.finders.internal.ui.profile.model.Action.NavigateBack
import ru.droidcat.feature.finders.internal.ui.profile.model.Action.SetReaction
import ru.droidcat.feature.finders.internal.ui.profile.model.Label
import ru.droidcat.feature.finders.internal.ui.profile.model.Label.BackRequested
import ru.droidcat.feature.finders.internal.ui.profile.model.Message
import ru.droidcat.feature.finders.internal.ui.profile.model.Message.SetProfile

internal class DefaultProfileExecutor(
    private val sendReactionUseCase: SendReactionUseCase,
    private val selectedProfileUseCase: SelectedProfileUseCase,
) : CoroutineExecutor<FinderProfileIntent, Action, ProfileState, Message, Label>(uiDispatcher) {

    override fun executeAction(action: Action) {
        super.executeAction(action)
        when (action) {
            is Init ->
                selectedProfileUseCase.selectedProfile
                    .onEach {
                        it?.let {
                            it.onSuccess {
                                dispatch(SetProfile(it))
                            }.onFailure {
                                forward(NavigateBack)
                            }
                        }
                    }
                    .launchIn(scope)

            is NavigateBack -> scope.launch {
                selectedProfileUseCase.update(null).onSuccess {
                    publish(BackRequested)
                }
            }

            is SetReaction -> scope.launch {
                sendReactionUseCase.sendReaction(Reaction.Like).onSuccess {
                    forward(NavigateBack)
                }
            }
        }
    }

    override fun executeIntent(intent: FinderProfileIntent) {
        super.executeIntent(intent)
        when (intent) {
            is OnBackClick -> forward(NavigateBack)

            is OnLike -> forward(SetReaction(Reaction.Like))

            is OnDislike -> forward(SetReaction(Reaction.Dislike))
        }
    }
}
