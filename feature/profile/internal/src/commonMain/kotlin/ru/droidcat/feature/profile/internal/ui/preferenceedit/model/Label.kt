package ru.droidcat.feature.profile.internal.ui.preferenceedit.model

internal sealed interface Label {

    data object DismissRequested : Label
}
