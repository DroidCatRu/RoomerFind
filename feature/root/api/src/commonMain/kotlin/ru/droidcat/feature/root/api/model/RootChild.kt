package ru.droidcat.feature.root.api.model

import ru.droidcat.feature.auth.api.ui.root.AuthComponent
import ru.droidcat.feature.main.api.MainComponent

sealed interface RootChild {

    data object SplashChild : RootChild

    data class AuthChild(val component: AuthComponent) : RootChild

    data class MainChild(val component: MainComponent) : RootChild
}
