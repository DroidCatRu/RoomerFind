package ru.roomerfind.backend.controller

import ru.droidcat.roomerfind.model.network.UserAuthDTO
import ru.droidcat.roomerfind.model.network.UserCredDTO
import ru.roomerfind.backend.plugins.InvalidEmailException
import ru.roomerfind.backend.plugins.InvalidPasswordException
import ru.roomerfind.backend.plugins.UserAlreadyExistsException
import ru.roomerfind.backend.plugins.UserNotFoundException
import ru.roomerfind.backend.repository.UserRepository
import ru.roomerfind.backend.utils.CryptoTool
import ru.roomerfind.backend.utils.TokenManager
import ru.roomerfind.backend.utils.isValidEmail
import ru.roomerfind.backend.utils.isValidPassword

class AuthController(
    private val userRepo: UserRepository,
    private val tokenManager: TokenManager,
    private val cryptoTool: CryptoTool,
) {

    fun register(model: UserAuthDTO): UserCredDTO {
        if (!model.email.isValidEmail) throw InvalidEmailException()
        if (!model.password.isValidPassword) throw InvalidPasswordException()
        if (userRepo.getUserByEmail(model.email) != null) throw UserAlreadyExistsException()

        val userId = userRepo.createUser(
            email = model.email,
            password = cryptoTool.encryptCBC(model.password),
        )

        return UserCredDTO(
            id = userId,
            email = model.email,
            token = tokenManager.generateJWTToken(
                id = userId,
                email = model.email,
            ),
        )
    }

    fun login(model: UserAuthDTO): UserCredDTO {
        val user = userRepo.getUserByEmail(model.email) ?: throw UserNotFoundException()
        if (cryptoTool.decryptCBC(user.password) != model.password) throw InvalidPasswordException()

        return UserCredDTO(
            id = user.id,
            email = model.email,
            token = tokenManager.generateJWTToken(
                id = user.id,
                email = model.email,
            )
        )
    }
}
