package ru.droidcat.feature.finders.api.data

import kotlinx.coroutines.flow.Flow
import ru.droidcat.roomerfind.model.network.UserInfoDTO

interface FindersLocalApi {

    suspend fun saveMatches(matches: List<UserInfoDTO>)

    suspend fun clearAllMatches()

    val matches: Flow<List<UserInfoDTO>>

    suspend fun saveLastInfo(info: UserInfoDTO)

    suspend fun clearLastInfo()

    val lastInfo: Flow<UserInfoDTO?>
}
