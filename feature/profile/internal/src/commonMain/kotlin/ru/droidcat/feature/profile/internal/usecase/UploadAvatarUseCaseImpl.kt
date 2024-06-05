package ru.droidcat.feature.profile.internal.usecase

import ru.droidcat.feature.profile.api.usecase.UploadAvatarUseCase
import ru.droidcat.feature.profile.internal.data.RemoteProfileRepository

class UploadAvatarUseCaseImpl(
    private val remoteProfileRepository: RemoteProfileRepository,
) : UploadAvatarUseCase {

    override suspend fun uploadAvatar(bytes: ByteArray): Result<Unit> =
        remoteProfileRepository.uploadAvatar(bytes)
}
