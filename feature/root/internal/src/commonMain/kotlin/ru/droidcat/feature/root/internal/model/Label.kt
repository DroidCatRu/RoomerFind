package ru.droidcat.feature.root.internal.model

internal sealed interface Label {

    data object UserLoggedIn : Label

    data object UserLoggedOut : Label
}
