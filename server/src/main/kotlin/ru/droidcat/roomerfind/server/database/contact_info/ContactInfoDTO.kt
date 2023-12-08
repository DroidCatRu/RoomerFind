package ru.droidcat.roomerfind.server.database.contact_info

import kotlinx.serialization.Serializable

@Serializable
class ContactInfoDTO(
    val phone: String,
    val email: String,
    val priority: Int // 0 for phone | 1 for email
)