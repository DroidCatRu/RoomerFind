package ru.droidcat.feature.main.api.model

import ru.droidcat.feature.finders.api.ui.root.FindersComponent
import ru.droidcat.feature.profile.api.ui.root.ProfileComponent

sealed interface MainChild {

    data class ProfileChild(val component: ProfileComponent) : MainChild

    data class FindersChild(val component: FindersComponent) : MainChild
}
