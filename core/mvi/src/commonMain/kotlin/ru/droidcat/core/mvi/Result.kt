package ru.droidcat.core.mvi

import kotlin.coroutines.cancellation.CancellationException

@Suppress("TooGenericExceptionCaught")
suspend fun <T> runSuspendCatching(block: suspend () -> T): Result<T> = try {
    Result.success(block())
} catch (cancellationException: CancellationException) {
    throw cancellationException
} catch (exception: Exception) {
    Result.failure(exception)
}

suspend fun <R, T> Result<T>.mapSuspendCatching(transform: suspend (T) -> R): Result<R> {
    return fold(
        onSuccess = {
            runSuspendCatching { transform(it) }
        }
        ,
        onFailure = {
            Result.failure(it)
        }
    )
}
