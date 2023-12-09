package ru.droidcat.feature.profile.api.ui.geoedit

import com.arkivanov.decompose.value.Value
import ru.droidcat.feature.map.api.MapComponent
import ru.droidcat.feature.profile.api.ui.geoedit.model.GeoEditState

interface GeoEditComponent {

    val viewState: Value<GeoEditState>

    val mapComponent: MapComponent

    fun onConfirm()

    fun onDismiss()
}
