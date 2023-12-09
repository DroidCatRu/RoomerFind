package ru.droidcat.roomerfind.server.database.user_photo

import kotlinx.serialization.Serializable

@Serializable
class UserPhotoDTO (
    val img: ByteArray
)