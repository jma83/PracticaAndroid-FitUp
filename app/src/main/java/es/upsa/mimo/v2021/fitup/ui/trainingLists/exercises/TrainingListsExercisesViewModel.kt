package es.upsa.mimo.v2021.fitup.ui.trainingLists.exercises

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.upsa.mimo.v2021.fitup.utils.Event
import es.upsa.mimo.v2021.fitup.utils.io
import es.upsa.mimo.v2021.fitup.utils.ui
import es.upsa.mimo.v2021.fitup.model.APIEntities.Exercise
import es.upsa.mimo.v2021.fitup.model.APIEntities.ExerciseDataSet
import es.upsa.mimo.v2021.fitup.model.APIEntities.Exercises
import es.upsa.mimo.v2021.fitup.model.DBEntities.TrainingListItem
import es.upsa.mimo.v2021.fitup.model.DBEntities.UserItem
import es.upsa.mimo.v2021.fitup.providers.ExerciseProvider
import es.upsa.mimo.v2021.fitup.providers.TrainingListsProvider
import es.upsa.mimo.v2021.fitup.providers.UserProvider
import kotlinx.coroutines.launch


class TrainingListsExercisesViewModel(private val exerciseProvider: ExerciseProvider, private val trainingListsProvider: TrainingListsProvider, private val userProvider: UserProvider): ViewModel() {
    private val _items = MutableLiveData<List<ExerciseDataSet>>()
    val items: LiveData<List<ExerciseDataSet>> get() = _items

    private val _navigateToDetail = MutableLiveData<Event<ExerciseDataSet>>()
    val navigateToDetail: LiveData<Event<ExerciseDataSet>> get() = _navigateToDetail


    fun onItemClicked(item: ExerciseDataSet) {
        _navigateToDetail.value = Event(item)
    }

    fun onLoad(trainingListItemId: Int?, email: String?) {
        viewModelScope.launch {
            io {
                if (email == null || trainingListItemId == null) return@io
                val userItem: UserItem? = userProvider.getUserByEmail(email)
                if (userItem == null) return@io
                val trainingListItem: TrainingListItem? = trainingListsProvider.getTrainingList(trainingListItemId, userItem)
                val exercises = getExercises(trainingListItem)
                var exerciseDataSets: List<ExerciseDataSet>? =
                    exercises?.exercises?.map { ExerciseDataSet(it, null) }
                ui {
                    _items.value = exerciseDataSets
                }
                exerciseDataSets = getExerciseDataSets(exercises?.exercises)
                ui {
                    _items.value = exerciseDataSets
                }
            }
        }
    }

    private fun getExercises(trainingListItem: TrainingListItem?): Exercises? {
        val exercises = trainingListItem?.exercises;
        if (exercises == null || exercises.isEmpty()){
            return null
        }
        return Exercises(exercises.map {
            Exercise(
                it.external_id, it.name!!, it.exercise_base,
                it.description!!, it.category, it.muscles!!, it.language)
        })
    }

    private suspend fun getExerciseDataSets(exercises: List<Exercise>?): List<ExerciseDataSet>? {
        return exerciseProvider.getExerciseDataSetFromExercises(exercises)
    }
}