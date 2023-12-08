package ru.droidcat.roomerfind.server.database.preferences

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import ru.droidcat.roomerfind.server.database.user_info.UserInfo
import ru.droidcat.roomerfind.server.features.user.RoommatePrefsReceiveRemote

object Preferences: IntIdTable() {

    val min_cost = Preferences.integer("min_cost")
    val max_cost = Preferences.integer("max_cost")
    val min_age = Preferences.integer("min_age")
    val max_age = Preferences.integer("max_age")
    val description = Preferences.varchar("description", 1000)
    val gender = Preferences.integer("gender")

    fun getPrefs(token: String): PreferencesDTO? {
        return try {
            transaction {
                val userInfo = UserInfo.getUserByToken(token)
                if (userInfo != null) {
                    Preferences.select { Preferences.id eq userInfo.preferences }.map {
                        PreferencesDTO (
                            min_cost = it[min_cost],
                            max_cost = it[max_cost],
                            min_age = it[min_age],
                            max_age = it[max_age],
                            description = it[description],
                            gender = it[gender]
                        )
                    }.single()
                } else {
                    null
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun updatePrefs(token: String, userPreferences: RoommatePrefsReceiveRemote): Boolean {
        if ((userPreferences.gender != 0) and (userPreferences.gender != 1))
            return false
        return try {
            transaction {
                val userInfo = UserInfo.getUserByToken(token)
                if (userInfo?.preferences != null ) {
                    Preferences.update ({ Preferences.id eq userInfo.preferences } ) {
                        it[min_cost] = userPreferences.min_cost
                        it[max_cost] = userPreferences.max_cost
                        it[min_age] = userPreferences.min_age
                        it[max_age] = userPreferences.max_age
                        it[description] = userPreferences.description
                        it[gender] = userPreferences.gender
                    }
                } else {
                    val preferencesRowId = Preferences.insertAndGetId {
                        it[min_cost] = userPreferences.min_cost
                        it[max_cost] = userPreferences.max_cost
                        it[min_age] = userPreferences.min_age
                        it[max_age] = userPreferences.max_age
                        it[description] = userPreferences.description
                        it[gender] = userPreferences.gender
                    }.value

                    val userId = UserInfo.getUserIdByToken(token)
                    UserInfo.update({ UserInfo.id eq userId }) {
                        it[preferences] = preferencesRowId
                    }
                }
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

}