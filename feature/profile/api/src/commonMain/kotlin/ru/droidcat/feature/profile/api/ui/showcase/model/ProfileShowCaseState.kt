package ru.droidcat.feature.profile.api.ui.showcase.model

sealed interface ProfileShowCaseState {

    data class Loaded(
        val name: String,
        val age: String,
        val avatar: String?,
        val description: String,
        val contacts: String,
        val minPrice: Int?,
        val maxPrice: Int?,
        val minAge: Int?,
        val maxAge: Int?,
        val lat: Double?,
        val long: Double?,
        val radius: Double?,
    ) : ProfileShowCaseState

    data object Loading : ProfileShowCaseState
}
