package ru.droidcat.feature.finders.internal.ui.matches

import com.arkivanov.decompose.ComponentContext
import ru.droidcat.core.mvi.BaseComponentWithStore
import ru.droidcat.feature.finders.api.ui.matches.MatchesComponent
import ru.droidcat.feature.finders.api.ui.matches.model.MatchesState
import ru.droidcat.feature.finders.internal.ui.matches.model.Intent
import ru.droidcat.feature.finders.internal.ui.matches.model.Label

internal class DefaultMatchesComponent(
    componentContext: ComponentContext,
    private val onProfile: () -> Unit,
    private val onMatchSelect: () -> Unit,
) : MatchesComponent, BaseComponentWithStore<Intent, MatchesState, Label>(
    componentContext = componentContext,
    storeFactory = { get<DefaultMatchesStore>() }
) {

    override fun onProfileRequest() {
        onProfile()
    }

    override fun onSelect() {
        onMatchSelect()
    }
}

fun createMatchesComponent(
    componentContext: ComponentContext,
    onProfile: () -> Unit = {},
    onMatchSelect: () -> Unit = {},
): MatchesComponent = DefaultMatchesComponent(
    componentContext = componentContext,
    onProfile = onProfile,
    onMatchSelect = onMatchSelect,
)
