package ru.droidcat.roomerfind.server.database.preferences

import kotlinx.serialization.Serializable

@Serializable
class PreferencesDTO(
    val min_cost: Int,
    val max_cost: Int,
    val min_age: Int,
    val max_age: Int,
    val description: String,
    val gender: Int // 0 for male | 1 for female
)