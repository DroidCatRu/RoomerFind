package ru.droidcat.feature.finders.internal.ui.matches

import com.arkivanov.mvikotlin.core.store.Reducer
import ru.droidcat.feature.finders.api.ui.matches.model.Match
import ru.droidcat.feature.finders.api.ui.matches.model.MatchesState
import ru.droidcat.feature.finders.internal.ui.matches.model.Message
import ru.droidcat.feature.finders.internal.ui.matches.model.Message.SetMatches

internal class DefaultMatchesReducer : Reducer<MatchesState, Message> {

    override fun MatchesState.reduce(msg: Message): MatchesState {
        return when (msg) {
            is SetMatches -> MatchesState.Loaded(
                matches = msg.matches.map {
                    Match(
                        name = it.name,
                        description = it.description,
                        age = it.age,
                    )
                },
            )
        }
    }
}
