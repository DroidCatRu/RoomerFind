package ru.droidcat.feature.auth.internal.usecase

import io.ktor.client.plugins.ClientRequestException
import ru.droidcat.feature.auth.api.data.AuthLocalApi
import ru.droidcat.feature.auth.api.model.InvalidCredentialsOrEmailAlreadyRegistered
import ru.droidcat.feature.auth.api.model.Token
import ru.droidcat.feature.auth.api.usecase.RegisterByEmailUseCase
import ru.droidcat.feature.auth.internal.data.RemoteAuthRepository
import ru.droidcat.roomerfind.model.network.StatusCode
import ru.droidcat.roomerfind.model.network.UserCredDTO

class RegisterByEmailUseCaseImpl(
    private val authRepository: RemoteAuthRepository,
    private val authLocalApi: AuthLocalApi,
) : RegisterByEmailUseCase {

    override suspend fun invoke(
        email: String,
        password: String,
    ): Result<Token> {
        return authRepository.register(email, password).toAuthToken()
    }

    private suspend fun Result<UserCredDTO>.toAuthToken(): Result<Token> = mapCatching {
        Token(it.token).also {
            authLocalApi.saveToken(it)
        }
    }.recoverCatching {
        if (it is ClientRequestException) {
            if (it.response.status.value == StatusCode.UserAlreadyExists.value) {
                throw InvalidCredentialsOrEmailAlreadyRegistered
            }
        }
        throw it
    }
}
