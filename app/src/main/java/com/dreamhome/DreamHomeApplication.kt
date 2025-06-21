package com.dreamhome

import android.app.Application
import com.dreamhome.data.dataModule
import com.dreamhome.network.networkModule
import org.koin.core.context.startKoin

class DreamHomeApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(
                listOf(
                    networkModule,
                    dataModule
                )
            )
        }
    }
}