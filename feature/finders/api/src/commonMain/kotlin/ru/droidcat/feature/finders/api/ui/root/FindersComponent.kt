package ru.droidcat.feature.finders.api.ui.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import ru.droidcat.feature.finders.api.ui.root.model.FindersChild

interface FindersComponent {

    val childStack: Value<ChildStack<*, FindersChild>>

    fun selectMatches()

    fun selectSearch()
}
