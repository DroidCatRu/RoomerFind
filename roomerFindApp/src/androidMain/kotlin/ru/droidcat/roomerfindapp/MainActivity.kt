package ru.droidcat.roomerfindapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import com.arkivanov.decompose.defaultComponentContext
import ru.droidcat.feature.auth.internal.root.createAuthComponent

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        val authComponent = createAuthComponent(
            componentContext = defaultComponentContext(),
        )

        setContent {
            App(
                mapProvider = AndroidMapProvider(LocalContext.current),
                authComponent = authComponent,
            )
        }
    }
}
