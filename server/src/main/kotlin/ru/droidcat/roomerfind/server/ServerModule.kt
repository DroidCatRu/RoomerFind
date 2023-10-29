package ru.droidcat.roomerfind.server

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.droidcat.roomerfind.server.config.Configuration
import ru.droidcat.roomerfind.server.config.createDatabaseSource
import ru.droidcat.roomerfind.server.config.createSqlDriver
import ru.droidcat.roomerfind.server.db.booleanAdapter
import ru.droidcat.roomerfind.server.db.instantAdapter
import ru.droidcat.roomerfind.server.service.UserSessionService
import ru.droidcat.roomerfind.server.service.UserSessionServiceImpl
import ru.droidcat.roomerfind.server.Otp.Adapter as OtpAdapter
import ru.droidcat.roomerfind.server.UserSession.Adapter as UserSessionAdapter

val serverModule = module {
    singleOf(::Configuration)
    singleOf(::createDatabaseSource)
    singleOf(::createSqlDriver)

    singleOf(::booleanAdapter)
    singleOf(::instantAdapter)
    singleOf(::UserSessionAdapter)
    singleOf(::OtpAdapter)

    singleOf(RoomerFindDb::invoke)
    single { get<RoomerFindDb>().userQueries }
    single { get<RoomerFindDb>().userSessionQueries }
    single { get<RoomerFindDb>().otpQueries }

    singleOf(::UserSessionServiceImpl) { bind<UserSessionService>() }
}
