package es.upsa.mimo.v2021.fitup.di

import es.upsa.mimo.v2021.fitup.providers.*
import es.upsa.mimo.v2021.fitup.ui.detail.DetailViewModel
import es.upsa.mimo.v2021.fitup.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<ExerciseProvider> { ExerciseProviderImpl }
    single<MuscleProvider> { MuscleProviderImpl }
    single<CategoryProvider> { CategoryProviderImpl }
    viewModel { HomeViewModel(get()) }
    viewModel { DetailViewModel(get(), get()) }
}