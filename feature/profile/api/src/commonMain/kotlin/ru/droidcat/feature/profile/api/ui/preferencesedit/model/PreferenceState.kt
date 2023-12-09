package ru.droidcat.feature.profile.api.ui.preferencesedit.model

sealed interface PreferenceState {
    data class Loaded(
        val minValue: String = String(),
        val maxValue: String = String(),
        val minAge: String = String(),
        val maxAge: String = String(),
    ) : PreferenceState

    data object Loading : PreferenceState
}
