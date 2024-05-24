package ru.droidcat.feature.profile.api.ui.showcase.model

sealed interface ProfileShowCaseIntent {

    data object OnEditProfile : ProfileShowCaseIntent

    data object OnEditPreferences : ProfileShowCaseIntent

    data object OnEditGeo : ProfileShowCaseIntent

    data object OnBack : ProfileShowCaseIntent

    data object OnLogOut : ProfileShowCaseIntent
}
