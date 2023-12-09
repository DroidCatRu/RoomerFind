package ru.droidcat.feature.finders.internal.ui.matches.model

import ru.droidcat.feature.finders.api.model.SuggestionInfo

internal sealed interface Message {

    data class SetMatches(
        val matches: List<SuggestionInfo.Defined>,
    ) : Message
}
