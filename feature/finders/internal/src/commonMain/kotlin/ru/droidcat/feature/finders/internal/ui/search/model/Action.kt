package ru.droidcat.feature.finders.internal.ui.search.model

import ru.droidcat.feature.finders.api.model.Reaction

internal sealed interface Action {

    data object Init : Action

    data object GetNext : Action

    data class Select(val id: Long) : Action

    data class SetReaction(val reaction: Reaction) : Action
}
