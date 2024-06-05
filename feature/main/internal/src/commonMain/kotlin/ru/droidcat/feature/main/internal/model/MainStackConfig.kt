package ru.droidcat.feature.main.internal.model

import kotlinx.serialization.Serializable

@Serializable
internal sealed interface MainStackConfig {

    @Serializable
    data object ProfileConfig : MainStackConfig

    @Serializable
    data object FindersConfig : MainStackConfig
}
