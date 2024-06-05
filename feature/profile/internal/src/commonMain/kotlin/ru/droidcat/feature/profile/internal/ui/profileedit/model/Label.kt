package ru.droidcat.feature.profile.internal.ui.profileedit.model

internal sealed interface Label {

    data object DismissRequested : Label
}
