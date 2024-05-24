package ru.roomerfind.backend.database.entity

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.ResultRow

object ReactionsTable : LongIdTable("reactions") {

    val initiator = long("initiator_id").references(UserTable.id, onDelete = ReferenceOption.CASCADE)
    val receiver = long("receiver_id").references(UserTable.id, onDelete = ReferenceOption.CASCADE)
    val reaction = enumeration("reaction", Reaction::class)

    enum class Reaction {
        LIKE,
        DISLIKE,
    }
}

data class Matches(
    val userIds: List<UserData>,
)

fun Iterable<ResultRow>.toMatches() = Matches(
    userIds = map {
        it.toUserData()
    },
)
