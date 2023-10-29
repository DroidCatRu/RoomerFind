package ru.droidcat.roomerfind.server.service

import ru.droidcat.roomerfind.model.UserSession

interface UserSessionService {

    fun registerUser(email: String)

    fun newSession(email: String, otp: String): UserSession?

    fun isValidSession(session: UserSession): Boolean

    fun endSession(session: UserSession)
}
