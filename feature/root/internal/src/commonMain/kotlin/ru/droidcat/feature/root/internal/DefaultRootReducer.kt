package ru.droidcat.feature.root.internal

import com.arkivanov.mvikotlin.core.store.Reducer
import ru.droidcat.feature.root.api.model.RootState
import ru.droidcat.feature.root.internal.model.Message

internal class DefaultRootReducer : Reducer<RootState, Message> {

    override fun RootState.reduce(msg: Message): RootState {
        return this
    }
}
