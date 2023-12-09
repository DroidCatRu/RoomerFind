package ru.droidcat.feature.finders.internal.data

import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory

expect val platformEngineFactory: HttpClientEngineFactory<HttpClientEngineConfig>
