package ru.droidcat.feature.finders.internal.ui.matches

import com.arkivanov.decompose.ComponentContext
import ru.droidcat.core.mvi.BaseComponentWithStore
import ru.droidcat.feature.finders.api.ui.matches.MatchesComponent
import ru.droidcat.feature.finders.api.ui.matches.model.MatchesIntent
import ru.droidcat.feature.finders.api.ui.matches.model.MatchesState
import ru.droidcat.feature.finders.internal.ui.matches.model.Label
import ru.droidcat.feature.finders.internal.ui.matches.model.Label.MatchSelected

internal class DefaultMatchesComponent(
    componentContext: ComponentContext,
    private val onMatchSelect: () -> Unit,
) : MatchesComponent, BaseComponentWithStore<MatchesIntent, MatchesState, Label>(
    componentContext = componentContext,
    storeFactory = { get<DefaultMatchesStore>() },
) {

    override fun onLabelReceive(label: Label) {
        super.onLabelReceive(label)
        when (label) {
            is MatchSelected -> onMatchSelect()
        }
    }
}

fun createMatchesComponent(
    componentContext: ComponentContext,
    onMatchSelect: () -> Unit = {},
): MatchesComponent = DefaultMatchesComponent(
    componentContext = componentContext,
    onMatchSelect = onMatchSelect,
)
