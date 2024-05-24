package ru.droidcat.feature.profile.internal.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withTimeoutOrNull
import ru.droidcat.core.coroutines.AbstractSuspendUseCase
import ru.droidcat.feature.profile.api.data.LocalProfileApi
import ru.droidcat.feature.profile.api.model.NoProfile
import ru.droidcat.feature.profile.api.model.TimeOutException
import ru.droidcat.feature.profile.api.usecase.GetProfileUseCase
import ru.droidcat.feature.profile.internal.data.RemoteProfileRepository
import ru.droidcat.roomerfind.model.network.UserInfoDTO
import kotlin.time.Duration.Companion.seconds

class GetProfileUseCaseImpl(
    private val localApi: LocalProfileApi,
    private val remoteProfileRepository: RemoteProfileRepository,
) : GetProfileUseCase, AbstractSuspendUseCase() {

    override val requests: Flow<Result<UserInfoDTO>> = localApi.profile.map {
        it?.let { Result.success(it) } ?: updateValue()
    }

    override suspend fun updateValue(): Result<UserInfoDTO> {
        localApi.clearProfile()
        return withTimeoutOrNull(5.seconds) {
            remoteProfileRepository.getProfile().onSuccess {
                localApi.saveProfile(it)
            }.recoverCatching {
                throw NoProfile
            }
        } ?: Result.failure(TimeOutException)
    }
}
