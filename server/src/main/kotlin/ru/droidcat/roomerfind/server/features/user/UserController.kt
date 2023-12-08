package ru.droidcat.roomerfind.server.features.user

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.droidcat.roomerfind.server.database.contact_info.ContactInfo
import ru.droidcat.roomerfind.server.database.geopositions.Geopositions
import ru.droidcat.roomerfind.server.database.preferences.Preferences
import ru.droidcat.roomerfind.server.database.user_info.UserInfo

class UserController(private val call: ApplicationCall) {

    suspend fun getUserInfo(token: String) {
        val userInfo = UserInfo.getUserByToken(token)
        if (userInfo != null) {
            call.respond(userInfo)
        } else {
            call.respond(HttpStatusCode.NotFound, "No such user")
        }
    }

    suspend fun setUserInfo(token: String) {
        val userInfoRemote = call.receive<UserInfoReceiveRemote>()
        if (UserInfo.updateUserInfo(token, userInfoRemote)) {
            call.respond(HttpStatusCode.OK)
        } else {
            call.respond(HttpStatusCode.InternalServerError, "Couldn't update user info")
        }
    }

    suspend fun getRoommatePreferences(token: String) {
        val userPreferences = Preferences.getPrefs(token)
        if (userPreferences != null) {
            call.respond(userPreferences)
        } else {
            call.respond(HttpStatusCode.NotFound, "No preferences for user")
        }
    }

    suspend fun setRoommatePreferences(token: String) {
        val userRoommatePrefs = call.receive<RoommatePrefsReceiveRemote>()
        if (Preferences.updatePrefs(token, userRoommatePrefs)) {
            call.respond(HttpStatusCode.OK)
        } else {
            call.respond(HttpStatusCode.InternalServerError, "Couldn't update user preferences")
        }
    }

    suspend fun getGeopositions(token: String) {
        call.respond(Geopositions.getGeopositions(token))
    }

    suspend fun addGeoposition(token: String) {
        val geoposReceiveRemote = call.receive<GeoposReceiveRemote>()
        if (Geopositions.addGeoposition(token, geoposReceiveRemote)) {
            call.respond(HttpStatusCode.OK)
        } else {
            call.respond(HttpStatusCode.InternalServerError, "Couldn't add for user")
        }
    }

    suspend fun removeGeoposition(token: String) {
        val geoposReceiveRemote = call.receive<GeoposReceiveRemote>()
        if (Geopositions.removeGeoposition(token, geoposReceiveRemote)) {
            call.respond(HttpStatusCode.OK)
        } else {
            call.respond(HttpStatusCode.InternalServerError, "Couldn't remove")
        }
    }

    suspend fun clearGeopositions(token: String){
        if (Geopositions.removeAllGeopositions(token)) {
            call.respond(HttpStatusCode.OK)
        } else {
            call.respond(HttpStatusCode.InternalServerError, "Couldn't clear user's")
        }
    }

    suspend fun getContactInfo(token: String) {
        val contactInfo = ContactInfo.getContacts(token)
        if (contactInfo != null) {
            call.respond(contactInfo)
        } else {
            call.respond(HttpStatusCode.NotFound, "No preferences for user")
        }
    }

    suspend fun setContactInfo(token: String) {
        val contactsReceiveRemote = call.receive<ContactsReceiveRemote>()
        if (ContactInfo.updateContacts(token, contactsReceiveRemote)) {
            call.respond(HttpStatusCode.OK)
        } else {
            call.respond(HttpStatusCode.InternalServerError, "Couldn't update user preferences")
        }
    }

}