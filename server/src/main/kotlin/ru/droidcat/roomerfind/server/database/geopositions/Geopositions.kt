package ru.droidcat.roomerfind.server.database.geopositions

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import ru.droidcat.roomerfind.server.database.user_info.UserInfo
import ru.droidcat.roomerfind.server.features.user.GeoposReceiveRemote
import ru.droidcat.roomerfind.server.features.user.UserIDReceiveRemote

object Geopositions : IntIdTable() {

    val lat = Geopositions.double("lat")
    val lon = Geopositions.double("lon")
    val rad = Geopositions.double("rad")
    val userId = reference("user_id", UserInfo) //Geopositions.integer("user_id").references(UserInfo.id)

    fun getGeopositions(token: String): List<GeopositionsDTO> {
        return try {
            transaction {
                val userId = UserInfo.getUserIdByToken(token)
                if (userId != null) {
                    Geopositions.select { Geopositions.userId eq userId }.map {
                        GeopositionsDTO(
                            userId = userId,
                            lat = it[Geopositions.lat],
                            lon = it[Geopositions.lon],
                            rad = it[Geopositions.rad]
                        )
                    }.toList()
                } else {
                    listOf()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            listOf()
        }
    }

    fun addGeoposition(token: String, geoposReceiveRemote: GeoposReceiveRemote): Boolean {
        return try {
            transaction {
                val userId = UserInfo.getUserIdByToken(token)
                if (userId != null) {
                    Geopositions.insert {
                        it[Geopositions.userId] = userId
                        it[Geopositions.lat] = geoposReceiveRemote.lat
                        it[Geopositions.lon] = geoposReceiveRemote.lon
                        it[Geopositions.rad] = geoposReceiveRemote.rad
                    }
                } else {
                    false
                }
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun removeGeoposition(token: String, geoposReceiveRemote: GeoposReceiveRemote): Boolean {
        return try {
            transaction {
                val userId = UserInfo.getUserIdByToken(token)
                if (userId != null) {
                    Geopositions.deleteWhere {
                        (Geopositions.userId eq userId) and
                        (Geopositions.lat eq geoposReceiveRemote.lat) and
                        (Geopositions.lon eq geoposReceiveRemote.lon) and
                        (Geopositions.rad eq geoposReceiveRemote.rad)
                    }
                } else {
                    false
                }
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun removeAllGeopositions(token: String): Boolean {
        return try {
            transaction {
                val userId = UserInfo.getUserIdByToken(token)
                if (userId != null) {
                    Geopositions.deleteWhere {
                        (Geopositions.userId eq userId)
                    }
                } else {
                    false
                }
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

}