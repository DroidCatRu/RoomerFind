package ru.droidcat.roomerfind.server.features.user

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.droidcat.roomerfind.server.database.contact_info.ContactInfo
import ru.droidcat.roomerfind.server.database.geopositions.Geopositions
import ru.droidcat.roomerfind.server.database.preferences.Preferences
import ru.droidcat.roomerfind.server.database.user_info.UserInfo
import ru.droidcat.roomerfind.server.database.user_photo.UserPhoto

class UserController(private val call: ApplicationCall) {

    /**
     * Fetches user info by access token
     *
     * input: Bearer Token (header)
     *
     * return: User Info object
     * */
    suspend fun getUserInfo(token: String) {
        val userInfo = UserInfo.getUserByToken(token)
        if (userInfo != null) {
            call.respond(userInfo)
        } else {
            call.respond(HttpStatusCode.NotFound, "No such user")
        }
    }

    /**
     * Sets new user info or modifies existing one by access token
     *
     * input: Bearer Token (header)
     *
     *        {name, age, description, gender} (body)
     * */
    suspend fun setUserInfo(token: String) {
        val userInfoRemote = call.receive<UserInfoReceiveRemote>()
        if (UserInfo.updateUserInfo(token, userInfoRemote)) {
            call.respond(HttpStatusCode.OK)
        } else {
            call.respond(HttpStatusCode.InternalServerError, "Couldn't update user info")
        }
    }

    /**
     * Fetches user preferences used in potential roommates filtering
     *
     * input: Bearer Token (header)
     *
     * return: Preferences object
     * */
    suspend fun getRoommatePreferences(token: String) {
        val userPreferences = Preferences.getPrefs(token)
        if (userPreferences != null) {
            call.respond(userPreferences)
        } else {
            call.respond(HttpStatusCode.NotFound, "No preferences for user")
        }
    }

    /**
     * Sets new user preferences or modifies existing one by access token
     *
     * input: Bearer Token (header)
     *
     *        {min_cost, max_cost, min_age, max_age, description, gender} (body)
     * */
    suspend fun setRoommatePreferences(token: String) {
        val userRoommatePrefs = call.receive<RoommatePrefsReceiveRemote>()
        if (Preferences.updatePrefs(token, userRoommatePrefs)) {
            call.respond(HttpStatusCode.OK)
        } else {
            call.respond(HttpStatusCode.InternalServerError, "Couldn't update user preferences")
        }
    }

    /**
     * Fetches user geopositions
     *
     * input: Bearer Token (header)
     *
     * return: List of Geopositions
     * */
    suspend fun getGeopositions(token: String) {
        call.respond(Geopositions.getGeopositions(token))
    }

    /**
     * Creates new user geoposition by access token
     *
     * input: Bearer Token (header)
     *
     *        {lat, lon, rad} (body)
     * */
    suspend fun addGeoposition(token: String) {
        val geoposReceiveRemote = call.receive<GeoposReceiveRemote>()
        if (Geopositions.addGeoposition(token, geoposReceiveRemote)) {
            call.respond(HttpStatusCode.OK)
        } else {
            call.respond(HttpStatusCode.InternalServerError, "Couldn't add for user")
        }
    }

    /**
     * Removes specific user geoposition by access token
     *
     * input: Bearer Token (header)
     *
     *        {lat, lon, rad} (body)
     * */
    suspend fun removeGeoposition(token: String) {
        val geoposReceiveRemote = call.receive<GeoposReceiveRemote>()
        if (Geopositions.removeGeoposition(token, geoposReceiveRemote)) {
            call.respond(HttpStatusCode.OK)
        } else {
            call.respond(HttpStatusCode.InternalServerError, "Couldn't remove")
        }
    }

    /**
     * Removes all user geoposition by access token
     *
     * input: Bearer Token (header)
     * */
    suspend fun clearGeopositions(token: String){
        if (Geopositions.removeAllGeopositions(token)) {
            call.respond(HttpStatusCode.OK)
        } else {
            call.respond(HttpStatusCode.InternalServerError, "Couldn't clear user's")
        }
    }

    /**
     * Fetches user contacts
     *
     * input: Bearer Token (header)
     *
     * return: Contacts object
     * */
    suspend fun getContactInfo(token: String) {
        val contactInfo = ContactInfo.getContacts(token)
        if (contactInfo != null) {
            call.respond(contactInfo)
        } else {
            call.respond(HttpStatusCode.NotFound, "No contacts exist")
        }
    }

    /**
     * Fetches user contacts
     *
     * input: Bearer Token (header)
     *
     *        userID id (body)
     *
     * return: Contacts object
     * */
    suspend fun getContactInfoById() {
        val id = call.receive<UserIDReceiveRemote>()
        val contactInfo = ContactInfo.getContactById(id.userId)
        if (contactInfo != null) {
            call.respond(contactInfo)
        } else {
            call.respond(HttpStatusCode.NotFound, "No contacts exist")
        }
    }

    /**
     * Sets new user contact info or modifies existing one by access token
     *
     * input: Bearer Token (header)
     *
     *        {phone, email, priority} (body)
     * */
    suspend fun setContactInfo(token: String) {
        val contactsReceiveRemote = call.receive<ContactsReceiveRemote>()
        if (ContactInfo.updateContacts(token, contactsReceiveRemote)) {
            call.respond(HttpStatusCode.OK)
        } else {
            call.respond(HttpStatusCode.InternalServerError, "Couldn't update user preferences")
        }
    }

    /**
     * Fetches user photo
     *
     * input: Bearer Token (header)
     *
     * return: ByteArray photo
     * */
    suspend fun getUserPhoto(token: String){
        val userPhotoDTO = UserInfo.getPhoto(token)
        if (userPhotoDTO != null) {
            call.respond(userPhotoDTO)
        } else {
            call.respond(HttpStatusCode.NotFound, "No contacts exist")
        }
    }

    /**
     * Sets new user photo or modifies existing one by access token
     *
     * input: Bearer Token (header)
     *
     *        {photo} (body)
     * */
    suspend fun setUserPhoto(token: String) {
        val userPhotoRemote = call.receive<UserPhotoRemote>()
        if (UserInfo.setPhoto(token, userPhotoRemote)) {
            call.respond(HttpStatusCode.OK)
        } else {
            call.respond(HttpStatusCode.InternalServerError, "Couldn't add for user")
        }
    }

}