package ru.roomerfind.backend.di

import com.typesafe.config.ConfigFactory
import io.ktor.server.config.ApplicationConfig
import io.ktor.server.config.HoconApplicationConfig
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.roomerfind.backend.database.entity.ReactionsTable
import ru.roomerfind.backend.database.entity.UserTable
import ru.roomerfind.backend.utils.CryptoTool
import ru.roomerfind.backend.utils.TokenManager

val mainModule = module {
    single<ApplicationConfig> { HoconApplicationConfig(ConfigFactory.load()) }

    singleOf(::CryptoTool)
    singleOf(::TokenManager)

    single<Database> {
        val config by inject<ApplicationConfig>()
        Database.connect(
            url = config.property("db.jdbcUrl").getString(),
            driver = config.property("db.jdbcDriver").getString(),
            user = config.property("db.dbUser").getString(),
            password = config.property("db.dbPassword").getString(),
        ).also {
            transaction(it) {
                SchemaUtils.createMissingTablesAndColumns(
                    UserTable,
                    ReactionsTable,
                )
            }
        }
    }
}
