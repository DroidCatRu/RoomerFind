package ru.droidcat.core.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren

interface SuspendUseCase {

    val useCaseScope: CoroutineScope

    fun release() {
        useCaseScope.coroutineContext.cancelChildren()
    }
}