package es.upsa.mimo.v2021.fitup.di

import es.upsa.mimo.v2021.fitup.providers.*
import es.upsa.mimo.v2021.fitup.ui.categories.CategoriesViewModel
import es.upsa.mimo.v2021.fitup.ui.activities.detail.DetailViewModel
import es.upsa.mimo.v2021.fitup.ui.exercises.ExercisesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<ExerciseProvider> { ExerciseProviderImpl }
    single<MuscleProvider> { MuscleProviderImpl }
    single<CategoryProvider> { CategoryProviderImpl }
    viewModel { ExercisesViewModel(get()) }
    viewModel { DetailViewModel(get(), get()) }
    viewModel { CategoriesViewModel(get()) }
}