package ru.droidcat.roomerfind.server.database.user_credentials

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import ru.droidcat.roomerfind.server.utils.hashPassword

object UserCredentials : IntIdTable() {

    val userId = UserCredentials.integer("user_id")
    val login = UserCredentials.varchar("login", 100)
    val password = UserCredentials.varchar("password", 500)

    fun insertCredentials(creds: UserCredentialsDTO): Int {
        return try {
            transaction {
                UserCredentials.insertAndGetId {
                    it[userId] = creds.userId
                    it[login] = creds.login
                    it[password] = creds.password
                }.value
            }
        } catch (e: Exception) {
            -1
        }
    }

    fun getIdByLogin(login: String): Int {
        return try {
            transaction {
                UserCredentials.select() { UserCredentials.login eq login }
                    .single()[UserCredentials.id].value
            }
        } catch (e: Exception) {
            return -1
        }
    }


    fun updateUserIdByLogin(login: String, userId: Int) {
        transaction {
            UserCredentials.update({ UserCredentials.login eq login }) {
                it[UserCredentials.userId] = userId
            }
        }
    }


    fun getCredentialsByLogin(login: String): UserCredentialsDTO?  {
        return try {
            transaction {
                val userCreds = UserCredentials.select() { UserCredentials.login.eq(login) }.single()
                UserCredentialsDTO(
                    login = userCreds[UserCredentials.login],
                    userId = userCreds[userId],
                    password = userCreds[password]
                )
            }
        } catch (e: Exception) {
            null
        }
    }
}