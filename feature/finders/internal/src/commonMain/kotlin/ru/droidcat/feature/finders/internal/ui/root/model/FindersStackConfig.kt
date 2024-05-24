package ru.droidcat.feature.finders.internal.ui.root.model

import kotlinx.serialization.Serializable

@Serializable
internal sealed interface FindersStackConfig {

    @Serializable
    data object TabsConfig : FindersStackConfig

    @Serializable
    data object ProfileConfig : FindersStackConfig
}
