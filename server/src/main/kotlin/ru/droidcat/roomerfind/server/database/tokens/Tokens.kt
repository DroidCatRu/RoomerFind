package ru.droidcat.roomerfind.server.database.tokens

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import ru.droidcat.roomerfind.server.database.user_credentials.UserCredentials

object Tokens : IntIdTable() {

    val token = Tokens.varchar("token", 500)
    val credentialId = reference("credential_id", UserCredentials)

    fun fetchTokens(): List<TokenDTO> {
        return try {
            transaction {
                Tokens.selectAll().toList()
                    .map {
                        TokenDTO(
                            credentialId = it[Tokens.credentialId].value,
                            token = it[Tokens.token]
                        )
                    }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun insertToken(token: TokenDTO): Int {
        return try {
            transaction {
                Tokens.insertAndGetId {
                    it[Tokens.token] = token.token
                    it[credentialId] =  token.credentialId
                }.value
            }
        } catch (e: Exception) {
            return -1
        }

    }

    fun containsToken(token: String): Boolean {
        return try {
            transaction {
                Tokens.select { Tokens.token eq token }.count() > 0
            }
        } catch (e: Exception) {
            false
        }
    }
}