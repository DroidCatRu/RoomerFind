package ru.droidcat.feature.profile.api.ui.preferencesedit.model

sealed interface PreferenceEditIntent {

    data class OnMinValueChange(val value: String) : PreferenceEditIntent

    data class OnMaxValueChange(val value: String) : PreferenceEditIntent

    data class OnMinAgeChange(val value: String) : PreferenceEditIntent

    data class OnMaxAgeChange(val value: String) : PreferenceEditIntent

    data object OnConfirm : PreferenceEditIntent

    data object OnDismiss : PreferenceEditIntent
}
