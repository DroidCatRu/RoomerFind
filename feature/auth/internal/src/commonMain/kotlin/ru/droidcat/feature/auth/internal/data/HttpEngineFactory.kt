package ru.droidcat.feature.auth.internal.data

import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory

expect val platformEngineFactory: HttpClientEngineFactory<HttpClientEngineConfig>
