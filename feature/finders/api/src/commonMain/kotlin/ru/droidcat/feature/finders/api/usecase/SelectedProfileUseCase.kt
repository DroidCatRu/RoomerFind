package ru.droidcat.feature.finders.api.usecase

import kotlinx.coroutines.flow.StateFlow
import ru.droidcat.roomerfind.model.network.UserInfoDTO

interface SelectedProfileUseCase {

    val selectedProfile: StateFlow<Result<UserInfoDTO>?>

    suspend fun update(id: Long?): Result<Unit>
}
