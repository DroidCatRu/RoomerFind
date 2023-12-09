package ru.droidcat.feature.map.compose

import androidx.compose.runtime.compositionLocalOf

val LocalMapProvider = compositionLocalOf<NativeMapProvider> {
    noLocalProvidedFor("mapProvider not found")
}

private fun noLocalProvidedFor(name: String): Nothing {
    error("CompositionLocal $name not present")
}
