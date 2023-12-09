package ru.droidcat.feature.finders.api.model

sealed interface SuggestionInfo {

    data class Defined(
        val id: Int,
        val name: String = "",
        val age: String = "",
        val description: String = "",
        val gender: Int = 0,
    ) : SuggestionInfo

    data object Empty : SuggestionInfo
}
