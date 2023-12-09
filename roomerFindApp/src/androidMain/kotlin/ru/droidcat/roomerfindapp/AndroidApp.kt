package ru.droidcat.roomerfindapp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import ru.droidcat.feature.root.internal.di.rootModule

class AndroidApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AndroidApp)
            modules(rootModule)
        }
    }
}
