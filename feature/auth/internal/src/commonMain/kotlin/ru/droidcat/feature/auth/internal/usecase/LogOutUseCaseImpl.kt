package ru.droidcat.feature.auth.internal.usecase

import ru.droidcat.feature.auth.api.data.AuthLocalApi
import ru.droidcat.feature.auth.api.usecase.LogOutUseCase

class LogOutUseCaseImpl(
    private val authLocalApi: AuthLocalApi,
) : LogOutUseCase {

    override suspend fun invoke() = authLocalApi.clearToken()
}
