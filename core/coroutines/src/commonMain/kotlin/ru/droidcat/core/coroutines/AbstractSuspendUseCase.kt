package ru.droidcat.core.coroutines

import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

abstract class AbstractSuspendUseCase : SuspendUseCase {

    final override val useCaseScope: CoroutineScope by lazy {
        CoroutineScope(
            context = SupervisorJob() +
                Dispatchers.Default +
                CoroutineExceptionHandler { _, e ->
                    Napier.e("handle unexpected error $e", e)
                },
        )
    }
}
