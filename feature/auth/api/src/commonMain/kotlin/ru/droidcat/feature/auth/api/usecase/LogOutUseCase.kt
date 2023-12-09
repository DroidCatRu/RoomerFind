package ru.droidcat.feature.auth.api.usecase

import ru.droidcat.core.mvi.runSuspendCatching
import ru.droidcat.feature.auth.api.data.AuthDB

class LogOutUseCase(
    private val db: AuthDB,
) {

    suspend operator fun invoke() = runSuspendCatching {
        db.logOut()
    }
}
