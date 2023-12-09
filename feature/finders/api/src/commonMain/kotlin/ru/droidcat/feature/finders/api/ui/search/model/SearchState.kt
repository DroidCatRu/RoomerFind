package ru.droidcat.feature.finders.api.ui.search.model

sealed interface SearchState {

    data class Loaded(
        val suggestions: List<Suggestion>,
    ) : SearchState

    data object Loading : SearchState
}

data class Suggestion(
    val id: Int? = null,
    val name: String = "",
    val age: String = "",
    val description: String = "",
)
