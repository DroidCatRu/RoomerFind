package ru.droidcat.feature.auth.api.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import ru.droidcat.core.coroutines.AbstractSuspendUseCase
import ru.droidcat.core.coroutines.GetOrLoadUseCase
import ru.droidcat.feature.auth.api.data.AuthLocalApi
import ru.droidcat.feature.auth.api.model.NoToken
import ru.droidcat.feature.auth.api.model.Token

class TokenUseCase(
    private val localApi: AuthLocalApi,
) : AbstractSuspendUseCase(), GetOrLoadUseCase<Token> {

    override val requests: Flow<Result<Token>> = localApi.token
        .map { it?.let { Result.success(Token(it)) } ?: Result.failure(NoToken) }
        .distinctUntilChanged()
        .shareIn(useCaseScope, started = SharingStarted.Eagerly, replay = 1)

    suspend fun getTokenForRequest(): String = localApi.token.first().orEmpty()
}
