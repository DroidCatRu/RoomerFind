package ru.droidcat.feature.finders.api.ui.matches.model

sealed interface MatchesIntent {

    data class OnSelect(val id: Long) : MatchesIntent
}
