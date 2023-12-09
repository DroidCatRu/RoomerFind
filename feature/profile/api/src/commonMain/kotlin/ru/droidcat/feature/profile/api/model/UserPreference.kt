package ru.droidcat.feature.profile.api.model

sealed interface UserPreference {

    data class Defined(
        val minValue: String = "1000",
        val maxValue: String = "30000",
        val minAge: String = "20",
        val maxAge: String = "25",
    ) : UserPreference

    data object Empty : UserPreference
}
