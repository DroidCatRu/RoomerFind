package ru.droidcat.roomerfindapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import com.arkivanov.decompose.defaultComponentContext
import ru.droidcat.feature.map.compose.AndroidMapProvider
import ru.droidcat.feature.root.internal.createRootComponent

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        val rootComponent = createRootComponent(
            componentContext = defaultComponentContext(),
        )

        setContent {
            App(
                mapProvider = AndroidMapProvider(LocalContext.current),
                rootComponent = rootComponent,
            )
        }
    }
}
