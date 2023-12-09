package ru.droidcat.feature.auth.internal.data

import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.darwin.Darwin

actual val platformEngineFactory: HttpClientEngineFactory<HttpClientEngineConfig> = Darwin
