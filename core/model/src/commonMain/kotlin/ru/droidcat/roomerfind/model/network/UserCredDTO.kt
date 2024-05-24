package ru.droidcat.roomerfind.model.network

import kotlinx.serialization.Serializable

@Serializable
data class UserCredDTO(
    val id: Long,
    val email: String,
    var token: String,
)
