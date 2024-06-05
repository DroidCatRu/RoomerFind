package ru.droidcat.feature.profile.api.ui.profileedit.model

sealed interface ProfileEditIntent {

    data class OnNameChange(val value: String) : ProfileEditIntent

    data class OnAgeChange(val value: String) : ProfileEditIntent

    data class OnDescChange(val value: String) : ProfileEditIntent

    data class OnContactsChange(val value: String) : ProfileEditIntent

    data object OnConfirm : ProfileEditIntent

    data object OnDismiss : ProfileEditIntent
}
