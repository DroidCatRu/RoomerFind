package ru.droidcat.feature.profile.internal.ui.geoedit.model

internal sealed interface Action {

    data object GetUserGeo : Action

    data object SaveProfile : Action
}
