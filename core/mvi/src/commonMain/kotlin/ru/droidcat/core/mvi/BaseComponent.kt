package ru.droidcat.core.mvi

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnDestroy
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.getOrCreateScope

abstract class BaseComponent(
    componentContext: ComponentContext,
) : ComponentContext by componentContext, KoinScopeComponent {

    final override val scope by getOrCreateScope()

    init {
        componentContext.lifecycle.doOnDestroy(scope::close)
    }
}
