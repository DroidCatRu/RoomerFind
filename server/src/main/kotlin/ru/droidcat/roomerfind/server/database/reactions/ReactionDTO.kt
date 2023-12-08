package ru.droidcat.roomerfind.server.database.reactions

import kotlinx.serialization.Serializable

@Serializable
class ReactionDTO (
    val userId: Int,
    val targetId: Int,
    val userReaction: Int, // 0 for "dislike" | 1 for "like"
    val targetReaction: Int // 0 for "dislike" | 1 for "like"
)