package ru.droidcat.roomerfind.server.database.user_photo

import org.jetbrains.exposed.dao.id.IntIdTable

object UserPhoto : IntIdTable() {

    val img = UserPhoto.binary("img", Int.MAX_VALUE)

}