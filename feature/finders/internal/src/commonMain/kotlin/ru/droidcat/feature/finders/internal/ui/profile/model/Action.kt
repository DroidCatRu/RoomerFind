package ru.droidcat.feature.finders.internal.ui.profile.model

import ru.droidcat.feature.finders.api.model.Reaction

internal sealed interface Action {

    data object Init : Action

    data object NavigateBack : Action

    data class SetReaction(val reaction: Reaction) : Action
}
