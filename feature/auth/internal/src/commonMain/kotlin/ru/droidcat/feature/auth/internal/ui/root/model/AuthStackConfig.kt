package ru.droidcat.feature.auth.internal.ui.root.model

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

internal sealed interface AuthStackConfig : Parcelable {

    @Parcelize
    data object LoginConfig : AuthStackConfig

    @Parcelize
    data object RegisterConfig : AuthStackConfig
}
