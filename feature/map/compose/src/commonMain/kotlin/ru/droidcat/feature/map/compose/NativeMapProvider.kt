package ru.droidcat.feature.map.compose

import ru.droidcat.feature.map.api.MapComponent

interface NativeMapProvider {

    fun createMap(component: MapComponent): NativeMapView
}
