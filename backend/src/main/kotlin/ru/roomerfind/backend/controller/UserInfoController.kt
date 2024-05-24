package ru.roomerfind.backend.controller

import ru.droidcat.roomerfind.model.network.UserInfoDTO
import ru.roomerfind.backend.mapper.toUserInfoDTO
import ru.roomerfind.backend.plugins.BadParametersException
import ru.roomerfind.backend.repository.UserRepository

class UserInfoController(
    private val userRepository: UserRepository,
) {

    fun updateInfo(
        userId: Long,
        info: UserInfoDTO,
    ) {
        if (info.name.isBlank() ||
            !userRepository.updateUserInfo(
                id = userId,
                info = info.copy(
                    contactInfo = info.contactInfo.takeIf { !it.isNullOrBlank() },
                ),
            )
        ) {
            throw BadParametersException()
        }
    }

    fun getInfo(
        userId: Long,
        currentId: Long,
    ): UserInfoDTO = userRepository
        .getUserData(userId, currentId)?.toUserInfoDTO() ?: throw BadParametersException()
}
