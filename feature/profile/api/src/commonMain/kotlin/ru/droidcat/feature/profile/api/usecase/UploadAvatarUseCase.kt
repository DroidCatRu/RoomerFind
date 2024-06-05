package ru.droidcat.feature.profile.api.usecase

interface UploadAvatarUseCase {

    suspend fun uploadAvatar(bytes: ByteArray): Result<Unit>
}
