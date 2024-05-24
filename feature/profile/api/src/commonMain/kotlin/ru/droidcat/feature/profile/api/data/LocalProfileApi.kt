package ru.droidcat.feature.profile.api.data

import kotlinx.coroutines.flow.Flow
import ru.droidcat.roomerfind.model.network.UserInfoDTO

interface LocalProfileApi {

    suspend fun saveProfile(profile: UserInfoDTO): Result<Unit>

    suspend fun clearProfile()

    val profile: Flow<UserInfoDTO?>
}
