package ru.droidcat.feature.root.api.model

import ru.droidcat.feature.auth.api.ui.root.AuthComponent
import ru.droidcat.feature.finders.api.ui.root.FindersComponent
import ru.droidcat.feature.profile.api.ui.root.ProfileComponent

sealed interface RootChild {

    data object SplashChild : RootChild

    data class AuthChild(val component: AuthComponent) : RootChild

    data class ProfileChild(val component: ProfileComponent) : RootChild

    data class FindersChild(val component: FindersComponent) : RootChild
}
