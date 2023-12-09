package ru.droidcat.feature.finders.internal.ui.root.model

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

internal sealed interface FindersStackConfig : Parcelable {

    @Parcelize
    data object MatchesConfig : FindersStackConfig

    @Parcelize
    data object SearchConfig : FindersStackConfig

    @Parcelize
    data object ProfileConfig : FindersStackConfig
}
