package ru.droidcat.feature.profile.internal.ui.profileedit.model

internal sealed interface Action {

    data object GetProfile : Action

    data object SaveProfile : Action
}
