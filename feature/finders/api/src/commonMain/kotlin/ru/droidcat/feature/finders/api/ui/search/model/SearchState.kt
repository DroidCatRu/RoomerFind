package ru.droidcat.feature.finders.api.ui.search.model

sealed interface SearchState {

    data class Loaded(
        val id: Long,
        val title: String,
        val priceRange: String?,
        val description: String?,
        val avatar: String?,
    ) : SearchState

    data object Loading : SearchState

    data object NoProfile : SearchState
}
