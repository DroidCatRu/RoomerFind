package ru.droidcat.feature.profile.api.ui.showcase.model

sealed interface ProfileShowCaseIntent {

    data class OnAvatarChange(val bytes: ByteArray) : ProfileShowCaseIntent

    data object OnEditProfile : ProfileShowCaseIntent

    data object OnEditPreferences : ProfileShowCaseIntent

    data object OnEditGeo : ProfileShowCaseIntent

    data object OnBack : ProfileShowCaseIntent

    data object OnLogOut : ProfileShowCaseIntent
}
