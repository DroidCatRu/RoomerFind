package ru.droidcat.roomerfind.server.features.main_logic

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.droidcat.roomerfind.server.database.reactions.Reactions
import ru.droidcat.roomerfind.server.database.user_info.UserInfo

class LogicController(private val call: ApplicationCall) {

    suspend fun getFinders(token: String){
        UserInfo.getFinders(token)
    }

    suspend fun react(token: String) {
        val reactionReceiveRemote = call.receive<ReactionReceiveRemote>()
        if (Reactions.reactToTarget(token, reactionReceiveRemote)) {
            call.respond(HttpStatusCode.OK)
        } else {
            call.respond(HttpStatusCode.InternalServerError, "Couldn't make reaction")
        }
    }

    suspend fun getMatches(token: String) {
        val matchedUsers = Reactions.getMatches(token)
        if (matchedUsers != null) {
            call.respond(matchedUsers)
        } else {
            call.respond(HttpStatusCode.InternalServerError, "Couldn't fetch matches")
        }
    }

}