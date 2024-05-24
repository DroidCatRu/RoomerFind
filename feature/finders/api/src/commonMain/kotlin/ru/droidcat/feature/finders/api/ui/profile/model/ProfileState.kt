package ru.droidcat.feature.finders.api.ui.profile.model

sealed interface ProfileState {

    data class Loaded(
        val avatar: String?,
        val title: String,
        val priceRange: String?,
        val description: String?,
        val contacts: String?,
    ) : ProfileState

    data object Loading : ProfileState
}
