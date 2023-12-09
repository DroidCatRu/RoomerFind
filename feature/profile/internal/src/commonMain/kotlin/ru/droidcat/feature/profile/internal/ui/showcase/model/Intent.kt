package ru.droidcat.feature.profile.internal.ui.showcase.model

internal sealed interface Intent {

    data object OnLogOut : Intent
}
