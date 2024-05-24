package ru.droidcat.feature.finders.api.ui.root.model

import ru.droidcat.feature.finders.api.ui.profile.FinderProfileComponent
import ru.droidcat.feature.finders.api.ui.tabs.FindersTabsComponent

sealed interface FindersChild {

    data class FindersTabsChild(val component: FindersTabsComponent) : FindersChild

    data class ProfileChild(val component: FinderProfileComponent) : FindersChild
}

sealed interface ChildWithNavigationBar
