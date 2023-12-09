package ru.droidcat.feature.profile.api.ui.profileedit.model

sealed interface ProfileEditState {

    data class Loaded(
        val name: String,
        val age: String,
        val description: String,
        val email: String,
        val phone: String,
    ) : ProfileEditState

    data object Loading : ProfileEditState
}
