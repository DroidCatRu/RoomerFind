package ru.droidcat.feature.profile.api.ui.showcase.model

sealed interface ProfileShowCaseState {

    data class Loaded(
        val name: String = String(),
        val age: String = String(),
        val description: String = String(),
        val email: String = String(),
        val phone: String = String(),
    ) : ProfileShowCaseState

    data object Loading : ProfileShowCaseState
}
