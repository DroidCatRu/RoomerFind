package ru.droidcat.feature.finders.internal.ui.profile

import com.arkivanov.decompose.ComponentContext
import ru.droidcat.core.mvi.BaseComponentWithStore
import ru.droidcat.feature.finders.api.ui.profile.FinderProfileComponent
import ru.droidcat.feature.finders.api.ui.profile.model.FinderProfileIntent
import ru.droidcat.feature.finders.api.ui.profile.model.ProfileState
import ru.droidcat.feature.finders.internal.ui.profile.model.Label
import ru.droidcat.feature.finders.internal.ui.profile.model.Label.BackRequested

internal class DefaultFinderProfileComponent(
    componentContext: ComponentContext,
    private val onBack: () -> Unit,
) : FinderProfileComponent, BaseComponentWithStore<FinderProfileIntent, ProfileState, Label>(
    componentContext = componentContext,
    storeFactory = { get<DefaultProfileStore>() },
) {

    override fun onLabelReceive(label: Label) {
        super.onLabelReceive(label)
        when (label) {
            is BackRequested -> onBack()
        }
    }
}

fun createFinderProfileComponent(
    componentContext: ComponentContext,
    onBack: () -> Unit = {},
): FinderProfileComponent = DefaultFinderProfileComponent(
    componentContext = componentContext,
    onBack = onBack,
)
