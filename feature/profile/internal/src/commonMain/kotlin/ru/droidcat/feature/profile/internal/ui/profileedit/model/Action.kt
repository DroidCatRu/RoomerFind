package ru.droidcat.feature.profile.internal.ui.profileedit.model

internal sealed interface Action {

    data object GetUserProfile : Action
}
