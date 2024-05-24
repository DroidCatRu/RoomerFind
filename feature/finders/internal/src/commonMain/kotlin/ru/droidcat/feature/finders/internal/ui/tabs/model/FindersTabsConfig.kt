package ru.droidcat.feature.finders.internal.ui.tabs.model

import kotlinx.serialization.Serializable

@Serializable
internal sealed interface FindersTabsConfig {

    @Serializable
    data object SearchConfig : FindersTabsConfig

    @Serializable
    data object MatchesConfig : FindersTabsConfig
}
