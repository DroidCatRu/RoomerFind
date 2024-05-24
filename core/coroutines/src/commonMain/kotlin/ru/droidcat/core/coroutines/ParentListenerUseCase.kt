package ru.droidcat.core.coroutines

import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

abstract class ParentListenerUseCase<T, P>(
    protected val parent: GetOrLoadUseCase<P>,
) : AbstractSuspendUseCase(),
    GetOrLoadUseCase<T> {

    protected val current = MutableStateFlow<Result<T>?>(null)
    private val filterCurrent = current.filterNotNull()

    private var job: Job? = null

    private val nowListenParent: Boolean
        get() = job?.isActive == true

    /**
     * Setup new current value.
     * Invoke if all parents has success value.
     */
    protected abstract suspend fun setPositiveValue(parent: Result<P>)

    open suspend fun updateWithParent(): Result<T> {
        parent.updateValue()
        return updateValue()
    }

    override val requests: Flow<Result<T>> by lazy {
        useCaseScope.launch {
            current.value?.onFailure {
                updateValue()
            }
            if (!nowListenParent || current.value == null) {
                loadParentValue()
            }
        }
        filterCurrent
    }

    override suspend fun updateValue(): Result<T> {
        syncValueWithParent(parent.requests.first())
        return filterCurrent.first()
    }

    private suspend fun loadParentValue() {
        val parentValue = parent.requests.distinctUntilChanged()
        val hasFirstParentValue = MutableStateFlow(false)
        job?.cancel()
        job = useCaseScope.launch {
            parentValue.collect {
                syncValueWithParent(it)
                hasFirstParentValue.value = true
            }
        }
        hasFirstParentValue.first { it }
    }

    private suspend fun syncValueWithParent(parent: Result<P>) {
        if (!setParentError(parent)) {
            setPositiveValue(parent)
        }
    }

    private fun setParentError(parent: Result<*>): Boolean {
        parent.onFailure {
            current.value = Result.failure(it)
        }
        return parent.isFailure
    }
}
