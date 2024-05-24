package ru.droidcat.feature.finders.api.ui.tabs

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import ru.droidcat.feature.finders.api.ui.tabs.model.FindersTabsChild

interface FindersTabsComponent {

    val childStack: Value<ChildStack<*, FindersTabsChild>>

    fun onProfile()

    fun navigateToSearch()

    fun navigateToMatches()
}
