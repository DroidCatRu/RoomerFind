package ru.droidcat.feature.finders.api.ui.matches

import com.arkivanov.decompose.value.Value
import ru.droidcat.feature.finders.api.ui.matches.model.MatchesState

interface MatchesComponent {

    val viewState: Value<MatchesState>

    fun onProfileRequest()

    fun onSelect()
}
