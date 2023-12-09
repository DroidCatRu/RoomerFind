package ru.droidcat.feature.finders.api.ui.matches.model

sealed interface MatchesState {

    data class Loaded(
        val matches: List<Match> = listOf(
            Match(
                name = "Test",
                age = "23",
                description = "no description",
            ),
        ),
    ) : MatchesState

    data object Loading : MatchesState
}

data class Match(
    val name: String = "",
    val description: String = "",
    val age: String = "",
)
