package ru.droidcat.feature.finders.api.ui.profile

import com.arkivanov.decompose.value.Value
import ru.droidcat.feature.finders.api.ui.profile.model.ProfileState

interface ProfileComponent {

    val viewState: Value<ProfileState>
}
