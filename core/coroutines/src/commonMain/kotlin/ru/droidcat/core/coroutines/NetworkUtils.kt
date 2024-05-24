package ru.droidcat.core.coroutines

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.withContext

suspend inline fun <reified T> requestWrapper(
    crossinline block: suspend () -> HttpResponse,
): Result<T> = requestWrapper(block, modifier = { body() })

suspend inline fun <reified T> requestWrapper(
    crossinline block: suspend () -> HttpResponse,
    modifier: HttpResponse.() -> T,
): Result<T> = runCatching { executeRequest(block).modifier() }

suspend inline fun <T> executeRequest(crossinline block: suspend () -> T): T =
    withContext(ioDispatcher) { block.invoke() }
