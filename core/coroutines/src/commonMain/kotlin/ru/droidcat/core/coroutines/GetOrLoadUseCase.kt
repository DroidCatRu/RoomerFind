package ru.droidcat.core.coroutines

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Use case can load data from anywhere, after value successfully received it is saved locally
 */
interface GetOrLoadUseCase<T> : SuspendUseCase {

    /**
     * Load if local value is failed or empty
     * else return local value
     * @return [NoSuchElementException] by default
     */
    val requests: Flow<Result<T>>

    /**
     * Update local value
     */
    suspend fun updateValue(): Result<T> = requests.first()

    /**
     * Runs job in the scope of an UseCase.
     * @return Returns the result of the update function.
     */
    suspend fun runUpdateInUseCaseScope(): Result<T> =
        suspendCoroutine { continuation ->
            useCaseScope.launch {
                val result = updateValue()
                continuation.resume(result)
            }
        }
}
