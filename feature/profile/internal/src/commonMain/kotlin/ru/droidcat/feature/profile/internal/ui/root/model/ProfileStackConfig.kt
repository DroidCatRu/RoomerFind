package ru.droidcat.feature.profile.internal.ui.root.model

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

internal sealed interface ProfileStackConfig : Parcelable {

    @Parcelize
    data object ShowCaseConfig : ProfileStackConfig

    @Parcelize
    data object EditProfileConfig : ProfileStackConfig

    @Parcelize
    data object EditPreferenceConfig : ProfileStackConfig

    @Parcelize
    data object EditGeoConfig : ProfileStackConfig
}
