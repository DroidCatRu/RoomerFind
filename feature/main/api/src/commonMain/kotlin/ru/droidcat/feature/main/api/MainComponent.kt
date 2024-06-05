package ru.droidcat.feature.main.api

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import ru.droidcat.feature.main.api.model.MainChild

interface MainComponent : BackHandlerOwner {

    val childStack: Value<ChildStack<*, MainChild>>

    fun onBackRequest()
}
