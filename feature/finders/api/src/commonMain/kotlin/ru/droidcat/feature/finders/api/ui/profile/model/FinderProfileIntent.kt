package ru.droidcat.feature.finders.api.ui.profile.model

sealed interface FinderProfileIntent {

    data object OnBackClick : FinderProfileIntent

    data object OnLike : FinderProfileIntent

    data object OnDislike : FinderProfileIntent
}
