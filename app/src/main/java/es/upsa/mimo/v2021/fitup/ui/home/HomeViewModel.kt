package es.upsa.mimo.v2021.fitup.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.upsa.mimo.v2021.fitup.utils.Event
import es.upsa.mimo.v2021.fitup.utils.io
import es.upsa.mimo.v2021.fitup.utils.ui
import es.upsa.mimo.v2021.fitup.model.APIEntities.*
import es.upsa.mimo.v2021.fitup.providers.ExerciseProvider
import kotlinx.coroutines.launch

class HomeViewModel(private val exerciseProvider: ExerciseProvider) : ViewModel() {

    private val _items = MutableLiveData<List<ExerciseDataSet>>()
    val items: LiveData<List<ExerciseDataSet>> get() = _items

    private val _navigateToDetail = MutableLiveData<Event<ExerciseDataSet>>()
    val navigateToDetail: LiveData<Event<ExerciseDataSet>> get() = _navigateToDetail


    fun onItemClicked(item: ExerciseDataSet) {
        _navigateToDetail.value = Event(item)
    }

    fun onLoad() {
        viewModelScope.launch {
            io {
                val exercises = getExercises()
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

    private suspend fun getExercises(): Exercises? {
        return exerciseProvider.getExercises(true)
    }

    private suspend fun getExerciseDataSets(exercises: List<Exercise>?): List<ExerciseDataSet>? {
        return exerciseProvider.getExerciseDataSetFromExercises(exercises)
    }

}