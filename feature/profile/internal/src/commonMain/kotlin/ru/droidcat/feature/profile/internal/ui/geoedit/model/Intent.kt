package ru.droidcat.feature.profile.internal.ui.geoedit.model

internal sealed interface Intent {

    data class OnLocationChange(
        val lat: Double,
        val lon: Double,
    ) : Intent

    data object OnConfirm : Intent
}
