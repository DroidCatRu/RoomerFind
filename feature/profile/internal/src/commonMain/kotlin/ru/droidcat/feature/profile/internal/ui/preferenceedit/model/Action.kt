package ru.droidcat.feature.profile.internal.ui.preferenceedit.model

internal sealed interface Action {

    data object GetProfile : Action

    data object SaveProfile : Action
}
