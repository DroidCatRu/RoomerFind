package ru.droidcat.feature.profile.internal.ui.showcase.model

internal sealed interface Action {

    data object Init : Action

    data object Update : Action

    data object LogOut : Action
}
