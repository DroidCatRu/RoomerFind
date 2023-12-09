package ru.droidcat.feature.profile.internal.ui.showcase.model

internal sealed interface Action {

    data object GetUserProfile : Action
}
