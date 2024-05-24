package ru.roomerfind.backend.database.entity

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ResultRow

object UserTable : LongIdTable("users") {

    val email = varchar("email", EMAIL_MAX_LENGTH)
    val password = varchar("password", PASSWORD_MAX_LENGTH)

    val name = varchar("name", NAME_MAX_LENGTH).default("Нет имени")
    val age = integer("age").default(0)
    val description = varchar("description", DESCRIPTION_MAX_LENGTH).default("")
    val avatar = varchar("avatarId", AVATAR_ID_MAX_LENGTH).nullable().default(null)
    val contactInfo = varchar("contactInfo", CONTACT_INFO_MAX_LENGTH).nullable().default(null)

    val lat = double("latitude").default(0.0)
    val long = double("longitude").default(0.0)
    val radiusKm = double("radiusKm").default(1.0)

    val minPrice = integer("minPrice").default(0)
    val maxPrice = integer("maxPrice").default(1_000_000)

    val minAge = integer("minAge").default(0)
    val maxAge = integer("maxAge").default(100)
}

data class UserData(
    val id: Long,
    val email: String,
    val password: String,

    val name: String,
    val age: Int,
    val description: String,
    val avatar: String? = null,
    val contactInfo: String? = null,

    val lat: Double = 0.0,
    val long: Double = 0.0,
    val radiusKm: Double = 1.0,

    val minPrice: Int = 0,
    val maxPrice: Int = 1_000_000,

    val minAge: Int = 0,
    val maxAge: Int = 100,
)

fun ResultRow.toUserData(
    showContacts: ResultRow.() -> Boolean = { false },
) = UserData(
    id = this[UserTable.id].value,
    email = this[UserTable.email],
    password = this[UserTable.password],

    name = this[UserTable.name],
    age = this[UserTable.age],
    description = this[UserTable.description],
    avatar = this[UserTable.avatar],
    contactInfo = if (showContacts()) this[UserTable.contactInfo].orEmpty() else null,

    lat = this[UserTable.lat],
    long = this[UserTable.long],
    radiusKm = this[UserTable.radiusKm],

    minPrice = this[UserTable.minPrice],
    maxPrice = this[UserTable.maxPrice],

    minAge = this[UserTable.minAge],
    maxAge = this[UserTable.maxAge],
)

private const val EMAIL_MAX_LENGTH = 64
private const val PASSWORD_MAX_LENGTH = 64
private const val AVATAR_ID_MAX_LENGTH = 1024
private const val NAME_MAX_LENGTH = 64
private const val DESCRIPTION_MAX_LENGTH = 1024
private const val CONTACT_INFO_MAX_LENGTH = 1024
