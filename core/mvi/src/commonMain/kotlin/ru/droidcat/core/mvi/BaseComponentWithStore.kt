package ru.droidcat.core.mvi

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.arkivanov.mvikotlin.core.binder.BinderLifecycleMode.CREATE_DESTROY
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.extensions.coroutines.bind
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.scope.Scope

abstract class BaseComponentWithStore<Intent : Any, State : Any, Label : Any>(
    componentContext: ComponentContext,
    private val storeFactory: Scope.() -> Store<Intent, State, Label>,
) : BaseComponent(
    componentContext = componentContext,
),
    IntentAcceptor<Intent> {

    private val store by lazy {
        instanceKeeper.getStore { scope.storeFactory() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val viewState: StateFlow<State> = store.stateFlow

    init {
        bind(componentContext.lifecycle, CREATE_DESTROY, Dispatchers.Unconfined) {
            store.labels.bindTo(::onLabelReceive)
        }
        componentContext.lifecycle.doOnDestroy(store::dispose)
    }

    override fun accept(intent: Intent) {
        store.accept(intent)
    }

    protected open fun onLabelReceive(label: Label) {}
}
