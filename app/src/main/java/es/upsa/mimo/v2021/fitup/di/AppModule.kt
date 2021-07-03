package es.upsa.mimo.v2021.fitup.di

import es.upsa.mimo.v2021.fitup.providers.*
import es.upsa.mimo.v2021.fitup.ui.categories.CategoriesViewModel
import es.upsa.mimo.v2021.fitup.ui.activities.detail.DetailViewModel
import es.upsa.mimo.v2021.fitup.ui.exercises.ExercisesViewModel
import es.upsa.mimo.v2021.fitup.ui.trainingLists.TrainingListsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<ExerciseProvider> { ExerciseProviderImpl }
    single<MuscleProvider> { MuscleProviderImpl }
    single<CategoryProvider> { CategoryProviderImpl }
    single<TrainingListsProvider> { TrainingListsProviderImpl }
    viewModel { ExercisesViewModel(get()) }
    viewModel { DetailViewModel(get(), get()) }
    viewModel { CategoriesViewModel(get()) }
    viewModel { TrainingListsViewModel(get()) }
}