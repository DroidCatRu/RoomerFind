package ru.droidcat.feature.finders.internal.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withTimeoutOrNull
import ru.droidcat.core.coroutines.AbstractSuspendUseCase
import ru.droidcat.feature.finders.api.data.FindersLocalApi
import ru.droidcat.feature.finders.api.model.NoFinders
import ru.droidcat.feature.finders.api.model.TimeOutException
import ru.droidcat.feature.finders.api.usecase.GetFinderUseCase
import ru.droidcat.feature.finders.internal.data.RemoteFindersRepository
import ru.droidcat.roomerfind.model.network.UserInfoDTO
import kotlin.time.Duration.Companion.seconds

class GetFinderUseCaseImpl(
    private val findersLocalApi: FindersLocalApi,
    private val remoteFindersRepository: RemoteFindersRepository,
) : GetFinderUseCase, AbstractSuspendUseCase() {

    override val requests: Flow<Result<UserInfoDTO>> = findersLocalApi.lastInfo.map {
        it?.let { Result.success(it) } ?: updateValue()
    }

    override suspend fun updateValue(): Result<UserInfoDTO> {
        findersLocalApi.clearLastInfo()
        return withTimeoutOrNull(5.seconds) {
            remoteFindersRepository.getNextSuggestion().onSuccess {
                findersLocalApi.saveLastInfo(it)
            }.recoverCatching {
                throw NoFinders
            }
        } ?: Result.failure(TimeOutException)
    }
}
