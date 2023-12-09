package ru.droidcat.feature.profile.internal.ui.preferenceedit.model

internal sealed interface Action {

    data object GetUserPreference : Action
}
