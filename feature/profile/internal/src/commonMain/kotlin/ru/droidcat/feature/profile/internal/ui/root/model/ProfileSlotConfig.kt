package ru.droidcat.feature.profile.internal.ui.root.model

import kotlinx.serialization.Serializable

@Serializable
internal sealed interface ProfileSlotConfig {

    @Serializable
    data object EditProfileConfig : ProfileSlotConfig

    @Serializable
    data object EditPreferenceConfig : ProfileSlotConfig

    @Serializable
    data object EditGeoConfig : ProfileSlotConfig
}
