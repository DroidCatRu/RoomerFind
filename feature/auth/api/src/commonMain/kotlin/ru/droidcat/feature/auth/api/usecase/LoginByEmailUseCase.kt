package ru.droidcat.feature.auth.api.usecase

import ru.droidcat.feature.auth.api.model.Token

interface LoginByEmailUseCase {

    suspend operator fun invoke(
        email: String,
        password: String,
    ): Result<Token>
}
