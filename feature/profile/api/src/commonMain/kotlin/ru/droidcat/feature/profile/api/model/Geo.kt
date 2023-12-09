package ru.droidcat.feature.profile.api.model

sealed interface Geo {

    data class Defined(
        val lat: Double = 0.0,
        val lon: Double = 0.0,
        val radius: Double = 10.0,
    ) : Geo

    data object Empty : Geo
}
