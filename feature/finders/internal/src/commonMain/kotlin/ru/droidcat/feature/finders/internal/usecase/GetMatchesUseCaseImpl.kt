package ru.droidcat.feature.finders.internal.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.droidcat.core.coroutines.AbstractSuspendUseCase
import ru.droidcat.feature.finders.api.data.FindersLocalApi
import ru.droidcat.feature.finders.api.usecase.GetMatchesUseCase
import ru.droidcat.feature.finders.internal.data.RemoteFindersRepository
import ru.droidcat.roomerfind.model.network.UserInfoDTO

class GetMatchesUseCaseImpl(
    private val api: RemoteFindersRepository,
    private val findersLocal: FindersLocalApi,
) : GetMatchesUseCase, AbstractSuspendUseCase() {

    override val requests: Flow<Result<List<UserInfoDTO>>> = findersLocal.matches.map {
        it.takeIf { it.isNotEmpty() }?.let { Result.success(it) } ?: loadMatches()
    }

    override suspend fun updateValue(): Result<List<UserInfoDTO>> = loadMatches().onSuccess {
        findersLocal.saveMatches(it)
        Result.success(it)
    }

    private suspend fun loadMatches(): Result<List<UserInfoDTO>> = api.getMatches().map { response ->
        response.profiles
    }
}
