package ru.droidcat.feature.profile.internal.ui.preferenceedit.model

internal sealed interface Intent {

    data class OnMinValueChange(val value: String) : Intent

    data class OnMaxValueChange(val value: String) : Intent

    data class OnMinAgeChange(val age: String) : Intent

    data class OnMaxAgeChange(val age: String) : Intent

    data object OnConfirm : Intent
}
