package ru.droidcat.roomerfind.server.features.user

import kotlinx.serialization.Serializable

@Serializable
data class UserInfoReceiveRemote(
    val name: String,
    val age: Int,
    val description: String,
    val gender: Int
)

@Serializable
data class UserIDReceiveRemote(
    val userId: Int
)

@Serializable
data class RoommatePrefsReceiveRemote(
    val min_cost: Int,
    val max_cost: Int,
    val min_age: Int,
    val max_age: Int,
    val description: String,
    val gender: Int
)

@Serializable
data class GeoposReceiveRemote(
    val lat: Double,
    val lon: Double,
    val rad: Double
)

@Serializable
data class ContactsReceiveRemote(
    val phone: String,
    val email: String,
    val priority: Int
)