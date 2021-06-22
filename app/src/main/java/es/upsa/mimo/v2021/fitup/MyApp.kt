package es.upsa.mimo.v2021.fitup

import android.app.Application
import es.upsa.mimo.v2021.fitup.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MyApp)
            androidFileProperties()
            modules(listOf(appModule))
        }
    }
}