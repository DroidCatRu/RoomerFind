package ru.droidcat.feature.finders.api.ui.profile.model

sealed interface ProfileState {

    data class Loaded(
        val name: String = "",
        val age: String = "",
        val desc: String = "",
        val phone: String? = null,
        val email: String? = null,
        val liked: Boolean = false,
    ) : ProfileState

    data object Loading : ProfileState
}
