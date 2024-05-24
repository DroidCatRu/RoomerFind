package ru.roomerfind.backend.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.roomerfind.backend.controller.AuthController
import ru.roomerfind.backend.controller.AvatarController
import ru.roomerfind.backend.controller.FinderController
import ru.roomerfind.backend.controller.UserInfoController
import ru.roomerfind.backend.repository.UserRepository

val userModule = module {
    singleOf(::UserRepository)
    singleOf(::AuthController)
    singleOf(::AvatarController)
    singleOf(::UserInfoController)
    singleOf(::FinderController)
}
