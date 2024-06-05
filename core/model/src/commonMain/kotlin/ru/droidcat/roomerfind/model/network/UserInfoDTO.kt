package ru.droidcat.roomerfind.model.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MatchesDTO(
    @SerialName("profiles")
    val profiles: List<UserInfoDTO>
)

@Serializable
data class UserInfoDTO(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
    @SerialName("age")
    val age: Int,
    @SerialName("avatar")
    val avatar: String? = null,
    @SerialName("description")
    val description: String,
    @SerialName("contacts")
    val contactInfo: String? = null,
    @SerialName("preferences")
    val preferences: UserPreferencesDTO = UserPreferencesDTO(),
)

@Serializable
data class UserPreferencesDTO(
    @SerialName("min_price")
    val minPrice: Int = 0,
    @SerialName("max_price")
    val maxPrice: Int = 100,
    @SerialName("min_age")
    val minAge: Int = 0,
    @SerialName("max_age")
    val maxAge: Int = 100,
    @SerialName("latitude")
    val lat: Double = 0.0,
    @SerialName("longitude")
    val long: Double = 0.0,
    @SerialName("zoom")
    val zoom: Double = 5.0,
    @SerialName("radius_km")
    val radius: Double = 1.0,
)
