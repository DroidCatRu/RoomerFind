package ru.droidcat.feature.profile.api.ui.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import ru.droidcat.feature.profile.api.ui.root.model.ProfileChild

interface ProfileComponent {

    val childStack: Value<ChildStack<*, ProfileChild>>
}
