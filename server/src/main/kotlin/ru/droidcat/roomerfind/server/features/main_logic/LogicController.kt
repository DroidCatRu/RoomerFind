package ru.droidcat.roomerfind.server.features.main_logic

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.droidcat.roomerfind.server.database.reactions.Reactions
import ru.droidcat.roomerfind.server.database.user_info.UserInfo
import ru.droidcat.roomerfind.server.database.user_info.UserInfoDTO

class LogicController(private val call: ApplicationCall) {

    /**
     * Searches for potential roommates for user based on his preferable location
     *
     * "Swipped" people are not shown
     *
     * input: Bearer Token (header)
     *
     * output: map of <UserId, UserInfo>
     * */
    suspend fun getFinders(token: String){
        val finders = UserInfo.getFinders(token)
        if (finders != null)
            call.respond(finders)
        else
            call.respond(mapOf<Int, UserInfoDTO>())
    }


    /**
     * Creating a reaction to another user (liked or not)
     *
     * input: Bearer Token (header)
     * */
    suspend fun react(token: String) {
        val reactionReceiveRemote = call.receive<ReactionReceiveRemote>()
        if (Reactions.reactToTarget(token, reactionReceiveRemote)) {
            call.respond(HttpStatusCode.OK)
        } else {
            call.respond(HttpStatusCode.InternalServerError, "Couldn't make reaction")
        }
    }

    /**
     * Searches for potential roommates with whom there is a mutual sympathy
     *
     * input: Bearer Token (header)
     * */
    suspend fun getMatches(token: String) {
        val matchedUsers = Reactions.getMatches(token)
        if (matchedUsers != null) {
            call.respond(matchedUsers)
        } else {
            call.respond(HttpStatusCode.InternalServerError, "Couldn't fetch matches")
        }
    }

}