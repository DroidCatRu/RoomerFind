package ru.droidcat.core.mvi

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.arkivanov.mvikotlin.core.binder.BinderLifecycleMode.CREATE_DESTROY
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.extensions.coroutines.bind
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import kotlinx.coroutines.Dispatchers
import org.koin.core.scope.Scope

abstract class BaseComponentWithStore<Intent : Any, State : Any, Label : Any>(
    componentContext: ComponentContext,
    private val storeFactory: Scope.() -> Store<Intent, State, Label>,
) : BaseComponent(
    componentContext = componentContext,
) {

    private val store by lazy {
        instanceKeeper.getStore { scope.storeFactory() }
    }

    val viewState: Value<State> = store.statesAsValue()

    init {
        bind(componentContext.lifecycle, CREATE_DESTROY, Dispatchers.Unconfined) {
            store.labels.bindTo(::onLabelReceive)
        }
        componentContext.lifecycle.doOnDestroy(store::dispose)
    }

    protected fun accept(intent: Intent) {
        store.accept(intent)
    }

    protected open fun onLabelReceive(label: Label) {}
}
