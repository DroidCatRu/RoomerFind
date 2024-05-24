package ru.droidcat.feature.finders.internal.usecase

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.droidcat.core.coroutines.AbstractSuspendUseCase
import ru.droidcat.feature.finders.api.usecase.SelectedProfileUseCase
import ru.droidcat.feature.finders.internal.data.RemoteFindersRepository
import ru.droidcat.roomerfind.model.network.UserInfoDTO

class SelectedProfileUseCaseImpl(
    private val remoteFindersRepository: RemoteFindersRepository,
) : SelectedProfileUseCase, AbstractSuspendUseCase() {

    private val _selectedProfile = MutableStateFlow<Result<UserInfoDTO>?>(null)

    override val selectedProfile: StateFlow<Result<UserInfoDTO>?> = _selectedProfile

    override suspend fun update(id: Long?): Result<Unit> = when (id) {
        null -> {
            _selectedProfile.value = null
            Result.success(Unit)
        }
        else -> {
            remoteFindersRepository.getProfileById(id)
                .also { _selectedProfile.value = it }
                .map {}
        }
    }
}
