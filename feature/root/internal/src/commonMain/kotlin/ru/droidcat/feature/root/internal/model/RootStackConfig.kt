package ru.droidcat.feature.root.internal.model

import kotlinx.serialization.Serializable

@Serializable
internal sealed interface RootStackConfig {

    @Serializable
    data object SplashConfig : RootStackConfig

    @Serializable
    data object AuthConfig : RootStackConfig

    @Serializable
    data object ProfileConfig : RootStackConfig

    @Serializable
    data object FindersConfig : RootStackConfig
}
