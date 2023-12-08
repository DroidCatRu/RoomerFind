package ru.droidcat.feature.auth.api.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import ru.droidcat.feature.auth.api.root.model.AuthChild

interface AuthComponent {

    val childStack: Value<ChildStack<*, AuthChild>>
}
