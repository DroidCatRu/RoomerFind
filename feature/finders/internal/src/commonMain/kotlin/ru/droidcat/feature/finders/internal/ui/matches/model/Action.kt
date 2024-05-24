package ru.droidcat.feature.finders.internal.ui.matches.model

internal sealed interface Action {

    data object Init : Action

    data object UpdateMatches : Action

    data class SelectProfile(val id: Long) : Action
}
