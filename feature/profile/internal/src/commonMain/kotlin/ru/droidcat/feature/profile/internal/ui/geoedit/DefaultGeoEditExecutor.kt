package ru.droidcat.feature.profile.internal.ui.geoedit

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import ru.droidcat.core.coroutines.uiDispatcher
import ru.droidcat.feature.profile.api.ui.geoedit.model.GeoEditIntent
import ru.droidcat.feature.profile.api.ui.geoedit.model.GeoEditState
import ru.droidcat.feature.profile.internal.ui.geoedit.model.Action
import ru.droidcat.feature.profile.internal.ui.geoedit.model.Label
import ru.droidcat.feature.profile.internal.ui.geoedit.model.Message

internal class DefaultGeoEditExecutor :
    CoroutineExecutor<GeoEditIntent, Action, GeoEditState, Message, Label>(uiDispatcher) {

    override fun executeAction(action: Action) {
        super.executeAction(action)
    }

    override fun executeIntent(intent: GeoEditIntent) {
        super.executeIntent(intent)
    }
}
