package ru.droidcat.roomerfind.server.database.user_info

import kotlinx.serialization.Serializable

@Serializable
class UserInfoDTO(

    val name: String,
    val age: Int,
    val description: String,
    val gender: Int, // 0 for male | 1 for female

    val photo: Int?,
    val credentials: Int,
    val preferences: Int?,
    val contacts: Int?
)