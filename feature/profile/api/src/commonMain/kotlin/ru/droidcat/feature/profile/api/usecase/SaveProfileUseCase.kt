package ru.droidcat.feature.profile.api.usecase

import ru.droidcat.roomerfind.model.network.UserInfoDTO

interface SaveProfileUseCase {

    suspend fun saveProfile(profile: UserInfoDTO): Result<Unit>
}
