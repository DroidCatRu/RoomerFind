package ru.droidcat.feature.profile.internal.data

import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory

expect val platformEngineFactory: HttpClientEngineFactory<HttpClientEngineConfig>
