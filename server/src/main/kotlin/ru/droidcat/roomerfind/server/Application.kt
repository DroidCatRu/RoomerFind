package ru.droidcat.roomerfind.server

import com.typesafe.config.ConfigFactory
import io.ktor.server.config.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.exists
import org.jetbrains.exposed.sql.transactions.transaction
import ru.droidcat.roomerfind.server.database.contact_info.ContactInfo
import ru.droidcat.roomerfind.server.database.geopositions.Geopositions
import ru.droidcat.roomerfind.server.database.preferences.Preferences
import ru.droidcat.roomerfind.server.database.reactions.Reactions
import ru.droidcat.roomerfind.server.database.tokens.Tokens
import ru.droidcat.roomerfind.server.database.user_credentials.UserCredentials
import ru.droidcat.roomerfind.server.database.user_info.UserInfo
import ru.droidcat.roomerfind.server.database.user_photo.UserPhoto
import ru.droidcat.roomerfind.server.plugins.*
import ru.droidcat.roomerfind.server.features.login.configureLoginRouting
import ru.droidcat.roomerfind.server.features.main_logic.configureLogicRouting
import ru.droidcat.roomerfind.server.features.register.configureRegisterRouting
import ru.droidcat.roomerfind.server.features.user.configureUserRouting

fun main() {

    val config = HoconApplicationConfig(ConfigFactory.load())

    try {
        Database.connect(
            url = config.property("db.DATABASE_CONNECTION_STRING").getString(),
            driver = "org.postgresql.Driver",
            user = config.property("db.POSTGRES_USER").getString(),
            password =config.property("db.POSTGRES_PASSWORD").getString()
        )

       transaction {
            SchemaUtils.create(UserInfo, UserPhoto, UserCredentials,
                Tokens, Preferences, Geopositions, ContactInfo, Reactions)
       }

        embeddedServer(Netty, port = 8080, host = "0.0.0.0"){
            configureAuthentication()
            configureSerialization()
            configureLoginRouting()
            configureRegisterRouting()
            configureUserRouting()
            configureLogicRouting()
        }.start(wait = true)
    }
    catch (e: Exception){
        e.printStackTrace()
    }
}