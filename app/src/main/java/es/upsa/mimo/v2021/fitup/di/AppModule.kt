package es.upsa.mimo.v2021.fitup.di

import es.upsa.mimo.v2021.fitup.providers.*
import es.upsa.mimo.v2021.fitup.ui.categories.CategoriesViewModel
import es.upsa.mimo.v2021.fitup.ui.categories.CategoryExercisesViewModel
import es.upsa.mimo.v2021.fitup.ui.detail.DetailViewModel
import es.upsa.mimo.v2021.fitup.ui.home.HomeViewModel
import es.upsa.mimo.v2021.fitup.ui.register.RegisterViewModel
import es.upsa.mimo.v2021.fitup.ui.settings.SettingsViewModel
import es.upsa.mimo.v2021.fitup.ui.start.StartViewModel
import es.upsa.mimo.v2021.fitup.ui.trainingLists.TrainingListsViewModel
import es.upsa.mimo.v2021.fitup.ui.trainingLists.add.AddToTrainingListViewModel
import es.upsa.mimo.v2021.fitup.ui.trainingLists.create.CreateTrainingListViewModel
import es.upsa.mimo.v2021.fitup.ui.trainingLists.exercises.TrainingListsExercisesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<ExerciseProvider> { ExerciseProviderImpl }
    single<MuscleProvider> { MuscleProviderImpl }
    single<CategoryProvider> { CategoryProviderImpl }
    single<TrainingListsProvider> { TrainingListsProviderImpl }
    single<UserProvider> { UserProviderImpl }
    viewModel { StartViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { DetailViewModel(get(), get()) }
    viewModel { CategoriesViewModel(get()) }
    viewModel { CategoryExercisesViewModel(get()) }
    viewModel { TrainingListsViewModel(get(), get()) }
    viewModel { TrainingListsExercisesViewModel(get(),get(), get()) }
    viewModel { CreateTrainingListViewModel(get(), get()) }
    viewModel { AddToTrainingListViewModel(get(), get()) }
    viewModel { SettingsViewModel(get()) }
}