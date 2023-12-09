package ru.droidcat.feature.root.internal.model

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

internal sealed interface RootStackConfig : Parcelable {

    @Parcelize
    data object SplashConfig : RootStackConfig

    @Parcelize
    data object AuthConfig : RootStackConfig

    @Parcelize
    data object ProfileConfig : RootStackConfig

    @Parcelize
    data object FindersConfig : RootStackConfig
}
