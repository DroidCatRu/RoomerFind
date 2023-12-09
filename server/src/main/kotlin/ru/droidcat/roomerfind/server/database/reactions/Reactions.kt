package ru.droidcat.roomerfind.server.database.reactions

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import ru.droidcat.roomerfind.server.database.preferences.Preferences
import ru.droidcat.roomerfind.server.database.preferences.PreferencesDTO
import ru.droidcat.roomerfind.server.database.user_info.UserInfo
import ru.droidcat.roomerfind.server.database.user_info.UserInfoDTO
import ru.droidcat.roomerfind.server.features.main_logic.ReactionReceiveRemote
import ru.droidcat.roomerfind.server.features.user.UserIDReceiveRemote

object Reactions: IntIdTable() {

    val userId = reference("user_id", UserInfo)
    val targetId = reference("target_id", UserInfo)
    val userReaction = Reactions.integer("user_reaction")
    val targetReaction = Reactions.integer("target_reaction")

    fun reactToTarget(token: String, reactionReceiveRemote: ReactionReceiveRemote): Boolean {
        return try {
            transaction {
                val userId = UserInfo.getUserIdByToken(token)
                if (userId != null) {
                    Reactions.insert {
                        it[Reactions.userId] = userId
                        it[targetId] = reactionReceiveRemote.targetId
                        it[userReaction] = reactionReceiveRemote.userReaction
                        it[targetReaction] = reactionReceiveRemote.targetReaction
                    }
                } else {
                    false
                }
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun getMatches(token: String): Map<Int, UserInfoDTO?>? {
        val matched = mutableMapOf<Int, UserInfoDTO?>()
        try {
            transaction {
                val userId = UserInfo.getUserIdByToken(token) ?: return@transaction null
                val matchedIds = Reactions
                    .select {
                        (Reactions.userId eq userId) and
                        (userReaction eq 1) and
                        (targetReaction eq 1)
                    }.map { it[targetId].value }
                    .toList()
                for (id in matchedIds) {
                    matched[id] = UserInfo.getUserById(UserIDReceiveRemote(id))
                }
            }
            return matched
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}