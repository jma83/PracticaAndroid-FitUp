package es.upsa.mimo.v2021.fitup

import android.app.Application
import es.upsa.mimo.v2021.fitup.providers.ExerciseProvider
import es.upsa.mimo.v2021.fitup.providers.ExerciseProviderImpl
import es.upsa.mimo.v2021.fitup.viewmodels.HomeViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module
import kotlin.coroutines.CoroutineContext

class MyApp: Application() {
    private val appModule = module {
        single<ExerciseProvider> { ExerciseProviderImpl }
        single<CoroutineContext>(named("ioDispatcher")) { Dispatchers.IO }
    }

    private val scopesModule = module {
        scope<HomeActivity> {
            viewModel {
                HomeViewModel(
                    get(),
                    get(named("ioDispatcher"))
                )
            }
        }

        /*scope<DetailActivity> {
            viewModel { DetailViewModel(get(), get(named("ioDispatcher"))) }
        }*/
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MyApp)
            androidFileProperties()
            modules( listOf(appModule, scopesModule))
        }
    }
}