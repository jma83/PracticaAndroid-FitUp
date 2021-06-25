package es.upsa.mimo.v2021.fitup.di

import es.upsa.mimo.v2021.fitup.providers.ExerciseProvider
import es.upsa.mimo.v2021.fitup.providers.ExerciseProviderImpl
import es.upsa.mimo.v2021.fitup.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<ExerciseProvider> { ExerciseProviderImpl }
    viewModel { HomeViewModel(get()) }
}