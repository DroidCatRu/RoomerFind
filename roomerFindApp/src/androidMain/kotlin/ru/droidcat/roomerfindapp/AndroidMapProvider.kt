package ru.droidcat.roomerfindapp

import android.content.Context
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.maps.MapView as MapboxView

private const val MAP_STYLE_URL = "https://maps.starline.ru/mapstyles/default/style.json"

class AndroidMapProvider(
    private val context: Context,
) : NativeMapProvider {

    override fun createMap(): NativeView {
        Mapbox.getInstance(context)
        return NativeView(
            view = MapboxView(context).apply {
                getMapAsync { map ->
                    map.setStyle(MAP_STYLE_URL)
                    map.uiSettings.isLogoEnabled = false
                    map.uiSettings.isAttributionEnabled = false
                }
            },
        )
    }
}
