package ru.droidcat.feature.profile.internal.usecase

import ru.droidcat.feature.profile.api.data.LocalProfileApi
import ru.droidcat.feature.profile.api.usecase.SaveProfileUseCase
import ru.droidcat.feature.profile.internal.data.RemoteProfileRepository
import ru.droidcat.roomerfind.model.network.UserInfoDTO

class SaveProfileUseCaseImpl(
    private val localProfileApi: LocalProfileApi,
    private val remoteProfileRepository: RemoteProfileRepository,
) : SaveProfileUseCase {

    override suspend fun saveProfile(profile: UserInfoDTO): Result<Unit> {
        return remoteProfileRepository.saveProfile(profile).onSuccess {
            localProfileApi.saveProfile(profile)
        }
    }
}
