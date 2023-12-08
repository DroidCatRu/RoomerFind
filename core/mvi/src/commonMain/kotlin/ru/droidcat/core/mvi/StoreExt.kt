package ru.droidcat.core.mvi

import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.extensions.coroutines.states
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

fun <T : Any> Store<*, T, *>.statesAsValue() = object : Value<T>() {
    override val value: T get() = state
    private val scope = CoroutineScope(SupervisorJob() + uiDispatcher)
    private var jobs = emptyMap<(T) -> Unit, Job>()

    override fun subscribe(observer: (T) -> Unit) {
        val job = scope.launch { states.collect { observer(it) } }
        this.jobs += observer to job
    }

    override fun unsubscribe(observer: (T) -> Unit) {
        val job = jobs[observer] ?: return
        this.jobs -= observer
        job.cancel()
    }
}

fun <T : Any> StateFlow<T>.asValue() = object : Value<T>() {
    override val value: T get() = this@asValue.value
    private val scope = CoroutineScope(SupervisorJob() + uiDispatcher)
    private var jobs = emptyMap<(T) -> Unit, Job>()

    override fun subscribe(observer: (T) -> Unit) {
        val job = scope.launch { collect { observer(it) } }
        this.jobs += observer to job
    }

    override fun unsubscribe(observer: (T) -> Unit) {
        val job = jobs[observer] ?: return
        this.jobs -= observer
        job.cancel()
    }
}