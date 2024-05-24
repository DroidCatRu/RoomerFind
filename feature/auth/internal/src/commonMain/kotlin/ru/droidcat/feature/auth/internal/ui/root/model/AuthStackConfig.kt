package ru.droidcat.feature.auth.internal.ui.root.model

import kotlinx.serialization.Serializable

@Serializable
internal sealed interface AuthStackConfig {

    @Serializable
    data object LoginConfig : AuthStackConfig

    @Serializable
    data object RegisterConfig : AuthStackConfig
}
