package ru.droidcat.feature.finders.api.ui.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import ru.droidcat.feature.finders.api.ui.root.model.FindersChild

interface FindersComponent : BackHandlerOwner {

    val childStack: Value<ChildStack<*, FindersChild>>

    fun onBackRequest()
}
