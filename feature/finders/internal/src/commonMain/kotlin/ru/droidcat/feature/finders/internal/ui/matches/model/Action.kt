package ru.droidcat.feature.finders.internal.ui.matches.model

internal sealed interface Action {

    data object GetMatches : Action
}
