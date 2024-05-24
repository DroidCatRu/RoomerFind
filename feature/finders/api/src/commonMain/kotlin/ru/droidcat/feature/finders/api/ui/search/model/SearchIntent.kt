package ru.droidcat.feature.finders.api.ui.search.model

sealed interface SearchIntent {

    data class OnFinderProfileTap(val id: Long) : SearchIntent

    data object OnLike : SearchIntent

    data object OnDislike : SearchIntent

    data object OnUpdate : SearchIntent
}
