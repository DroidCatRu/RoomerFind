package ru.droidcat.feature.profile.internal.data

import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.okhttp.OkHttp

actual val platformEngineFactory: HttpClientEngineFactory<HttpClientEngineConfig> = OkHttp
