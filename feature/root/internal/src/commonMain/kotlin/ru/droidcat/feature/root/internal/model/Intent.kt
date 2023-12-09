package ru.droidcat.feature.root.internal.model

internal sealed interface Intent {

    data object OnStart : Intent
}
