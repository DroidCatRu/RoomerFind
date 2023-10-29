package ru.droidcat.roomerfind.server.service

import com.benasher44.uuid.uuidOf
import kotlinx.datetime.Clock
import ru.droidcat.roomerfind.model.UserSession
import ru.droidcat.roomerfind.server.OtpQueries
import ru.droidcat.roomerfind.server.UserQueries
import ru.droidcat.roomerfind.server.UserSessionQueries
import kotlin.random.Random
import kotlin.time.Duration.Companion.minutes
import ru.droidcat.roomerfind.server.UserSession as DbUserSession

class UserSessionServiceImpl(
    private val userQueries: UserQueries,
    private val userSessionQueries: UserSessionQueries,
    private val otpQueries: OtpQueries,
) : UserSessionService {

    override fun registerUser(email: String) {
        val userId = uuidOf(email.encodeToByteArray()).toString()
        userQueries.addUser(userId, email)
        otpQueries.insert(email, userId, generateOtp(), generateExpiredTime())
    }

    private fun generateOtp() = buildString {
        repeat(OTP_LENGTH) {
            append(Random.nextInt(MIN_OTP_NUMBER, MAX_OTP_NUMBER))
        }
    }

    private fun generateExpiredTime() = Clock.System.now().plus(OTP_LIFETIME_IN_MINS.minutes)

    override fun newSession(email: String, otp: String): UserSession? = otpQueries
        .select(email, otp)
        .executeAsList()
        .findLast { it.expiredAt <= Clock.System.now() }
        ?.let { otpFromDb ->
            UserSession(
                userId = otpFromDb.userId,
                sessionId = (otpFromDb.toString().encodeToByteArray()).toString()
            )
        }
        ?.also { userSessionQueries.insertFullSession(it.asDbEntity) }

    override fun isValidSession(session: UserSession): Boolean = userSessionQueries
        .getSession(
            userId = session.userId,
            sessionId = session.sessionId,
        )
        .executeAsOneOrNull()
        ?.isValid ?: false

    override fun endSession(session: UserSession) = userSessionQueries
        .endSession(
            userId = session.userId,
            sessionId = session.sessionId,
        )
}

internal val UserSession.asDbEntity
    get() = DbUserSession(
        userId = userId,
        sessionId = sessionId,
        isValid = true,
    )

private const val OTP_LENGTH = 6
private const val MIN_OTP_NUMBER = 0
private const val MAX_OTP_NUMBER = 9
private const val OTP_LIFETIME_IN_MINS = 10