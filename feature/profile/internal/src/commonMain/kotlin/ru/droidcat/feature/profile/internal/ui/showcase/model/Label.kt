package ru.droidcat.feature.profile.internal.ui.showcase.model

internal sealed interface Label {

    data object EditProfileRequested : Label

    data object EditPreferencesRequested : Label

    data object EditGeoRequested : Label

    data object BackRequested : Label
}
