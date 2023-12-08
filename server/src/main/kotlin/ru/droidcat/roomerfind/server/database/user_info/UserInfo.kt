package ru.droidcat.roomerfind.server.database.user_info

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import ru.droidcat.roomerfind.server.database.contact_info.ContactInfo
import ru.droidcat.roomerfind.server.database.preferences.Preferences
import ru.droidcat.roomerfind.server.database.tokens.Tokens
import ru.droidcat.roomerfind.server.database.user_credentials.UserCredentials
import ru.droidcat.roomerfind.server.database.user_photo.UserPhoto
import ru.droidcat.roomerfind.server.database.user_photo.UserPhotoDTO
import ru.droidcat.roomerfind.server.features.user.UserIDReceiveRemote
import ru.droidcat.roomerfind.server.features.user.UserInfoReceiveRemote
import ru.droidcat.roomerfind.server.features.user.UserPhotoRemote

object UserInfo : IntIdTable() {

    val name = UserInfo.varchar("name", 50)
    val age = UserInfo.integer("age")
    val description = UserInfo.varchar("description", 1000)
    val gender = UserInfo.integer("gender")

    val photo = reference("photo", UserPhoto).nullable() //UserCredentials.integer("photo").references(UserPhoto.id)
    val credentials = reference("credentials", UserCredentials) //UserCredentials.integer("credentials").references(UserCredentials.id)
    val preferences = reference("preferences", Preferences).nullable() //UserCredentials.integer("preferences").references(Preferences.id)
    val contacts = reference("contacts", ContactInfo).nullable() //UserCredentials.integer("contacts").references(ContactInfo.id)

    fun insertUserInfo(userInfoDTO: UserInfoDTO): Int {
        return try {
            transaction {
                UserInfo.insertAndGetId{
                    it[name] = userInfoDTO.name
                    it[age] = userInfoDTO.age
                    it[description] = userInfoDTO.description
                    it[gender] = userInfoDTO.gender

                    it[photo] = userInfoDTO.photo
                    it[credentials] = userInfoDTO.credentials
                    it[preferences] = userInfoDTO.preferences
                    it[contacts] = userInfoDTO.contacts
                }.value
            }
        } catch (e: Exception) {
            e.printStackTrace()
            -1
        }

    }

    fun getUserIdByToken(token: String): Int? {
        return try {
            transaction {
                Join(
                    UserCredentials, Tokens,
                    onColumn = UserCredentials.id, otherColumn = Tokens.credentialId,
                    joinType = JoinType.INNER,
                    additionalConstraint = { Tokens.token eq token }
                ).selectAll().single()[UserCredentials.userId]
            }
        } catch (e: Exception) {
            null
        }
    }

    fun getUserByToken(token: String): UserInfoDTO?{
        return try {
            transaction {
                val userId = Join(
                    UserCredentials, Tokens,
                    onColumn = UserCredentials.id, otherColumn = Tokens.credentialId,
                    joinType = JoinType.INNER,
                    additionalConstraint = {Tokens.token eq token})
                    .selectAll()
                    .single()[UserCredentials.userId]

                UserInfo.select() { UserInfo.id eq userId }.map {
                    UserInfoDTO(
                        name = it[UserInfo.name],
                        age = it[UserInfo.age],
                        description = it[UserInfo.description],
                        gender = it[UserInfo.gender],

                        photo = it[UserInfo.photo]?.value,
                        credentials = it[UserInfo.credentials].value,
                        preferences = it[UserInfo.preferences]?.value,
                        contacts = it[UserInfo.contacts]?.value
                    )
                }.single()
            }
        } catch (e: Exception) {
            null
        }
    }

    fun getUserById(userIdRemote: UserIDReceiveRemote): UserInfoDTO? {
        return try {
            transaction {
                UserInfo.select { UserInfo.id eq userIdRemote.userId }.map {
                    UserInfoDTO(
                        name = it[UserInfo.name],
                        age = it[UserInfo.age],
                        description = it[UserInfo.description],
                        gender = it[UserInfo.gender],

                        photo = it[UserInfo.photo]?.value,
                        credentials = it[UserInfo.credentials].value,
                        preferences = it[UserInfo.preferences]?.value,
                        contacts = it[UserInfo.contacts]?.value
                    )
                }.single()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun updateUserInfo(token: String, userInfo: UserInfoReceiveRemote): Boolean {
        if ((userInfo.gender != 0) and (userInfo.gender != 1))
            return false
        return try {
            transaction {
                val userId = getUserIdByToken(token)
                UserInfo.update({ UserInfo.id eq userId}) {
                    it[UserInfo.name] = userInfo.name
                    it[UserInfo.age] = userInfo.age
                    it[UserInfo.description] = userInfo.description
                    it[UserInfo.gender] = userInfo.gender
                }
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun getFinders(token: String): Map<Int, UserInfoDTO?>? {
        val matchedFinders = mutableMapOf<Int, UserInfoDTO?>()

        try {
            transaction {
                val userId = getUserIdByToken(token) ?: return@transaction null

                TransactionManager.current().exec(
                    "SELECT f.user_id, f.lat, f.lon, f.rad " +
                            "FROM geopositions f " +
                            "INNER JOIN geopositions u on u.user_id <> f.user_id " +
                            "WHERE sqrt(power((f.lat - u.lat),2) + power((f.lon - u.lon),2)) <= (f.rad + u.rad) " +
                                "AND u.user_id = $userId " +
                                "AND f.user_id NOT IN ( " +
                                        "SELECT target_id " +
                                        "FROM reactions " +
                                        "WHERE user_id = $userId )" ){rs ->
                    while (rs.next()){
                        val id = rs.getString("user_id").toInt()
                        matchedFinders[id] = UserInfo.getUserById(UserIDReceiveRemote(id))
                    }
                }
            }
            return matchedFinders
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    fun getPhoto(token: String): UserPhotoDTO? {
        return try {
            transaction {
                val user = getUserByToken(token) ?: return@transaction null

                UserPhoto.select{UserPhoto.id eq user.photo}.map {
                    UserPhotoDTO(img = it[UserPhoto.img])
                }.single()
            }
        } catch (e:Exception) {
            e.printStackTrace()
            null
        }
    }

    fun setPhoto(token: String, photo: UserPhotoRemote): Boolean {
        return try {
            transaction {

                val photoRowId = UserPhoto.insertAndGetId{
                    it[UserPhoto.img] = photo.photo
                }.value

                val userId = getUserIdByToken(token)
                UserInfo.update({ UserInfo.id eq userId }) {
                    it[UserInfo.photo] = photoRowId
                }
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}