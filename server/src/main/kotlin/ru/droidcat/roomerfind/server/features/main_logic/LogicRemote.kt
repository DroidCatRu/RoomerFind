package ru.droidcat.roomerfind.server.features.main_logic

import kotlinx.serialization.Serializable

@Serializable
data class ReactionReceiveRemote (
    val targetId: Int,
    val reaction: Int
)