package ru.droidcat.feature.finders.api.model

sealed interface Reaction {

    data object Like : Reaction

    data object Dislike : Reaction
}
