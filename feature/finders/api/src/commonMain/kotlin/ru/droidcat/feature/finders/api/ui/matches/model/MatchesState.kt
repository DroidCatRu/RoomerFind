package ru.droidcat.feature.finders.api.ui.matches.model

sealed interface MatchesState {

    data class Loaded(
        val matches: List<Match>,
    ) : MatchesState

    data object Loading : MatchesState
}

data class Match(
    val id: Long,
    val title: String,
    val priceRange: String?,
    val avatar: String?,
    val description: String?,
)
