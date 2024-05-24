package ru.roomerfind.backend.controller

import ru.droidcat.roomerfind.model.network.MatchesDTO
import ru.droidcat.roomerfind.model.network.UserInfoDTO
import ru.roomerfind.backend.mapper.toUserInfoDTO
import ru.roomerfind.backend.plugins.BadParametersException
import ru.roomerfind.backend.repository.UserRepository

class FinderController(
    private val userRepository: UserRepository,
) {

    fun getNextFinderForUser(
        userId: Long,
    ): UserInfoDTO = userRepository
        .getNextFinder(userId)
        ?.toUserInfoDTO() ?: throw BadParametersException()

    fun likeLast(userId: Long) = userRepository.likeLastFinder(userId)

    fun dislikeLast(userId: Long) = userRepository.dislikeLastFinder(userId)

    fun getMatches(
        userId: Long,
    ): MatchesDTO = userRepository
        .getMatches(userId)
        .map { it.toUserInfoDTO() }
        .let { MatchesDTO(it) }
}
