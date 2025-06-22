package com.dreamhome

import android.app.Application
import com.dreamhome.data.dataModule
import com.dreamhome.network.networkModule
import com.dreamhome.ui.UiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DreamHomeApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@DreamHomeApplication)
            modules(
                listOf(
                    networkModule,
                    dataModule,
                    UiModule
                )
            )
        }
    }
}