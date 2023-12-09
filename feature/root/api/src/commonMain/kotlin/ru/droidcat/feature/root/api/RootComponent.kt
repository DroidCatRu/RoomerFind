package ru.droidcat.feature.root.api

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import ru.droidcat.feature.root.api.model.RootChild

interface RootComponent {

    val childStack: Value<ChildStack<*, RootChild>>
}
