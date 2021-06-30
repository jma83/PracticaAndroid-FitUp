package es.upsa.mimo.v2021.fitup.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.upsa.mimo.v2021.fitup.Event
import es.upsa.mimo.v2021.fitup.extensions.io
import es.upsa.mimo.v2021.fitup.extensions.ui
import es.upsa.mimo.v2021.fitup.model.APIEntities.Category
import es.upsa.mimo.v2021.fitup.model.APIEntities.ExerciseDataSet
import es.upsa.mimo.v2021.fitup.providers.ExerciseProvider
import kotlinx.coroutines.launch

class ExercisesViewModel(private val exerciseProvider: ExerciseProvider) : ViewModel() {

    private val _items = MutableLiveData<List<ExerciseDataSet>>()
    val items: LiveData<List<ExerciseDataSet>> get() = _items

    private val _navigateToDetail = MutableLiveData<Event<ExerciseDataSet>>()
    val navigateToDetail: LiveData<Event<ExerciseDataSet>> get() = _navigateToDetail


    fun onItemClicked(item: ExerciseDataSet) {
        _navigateToDetail.value = Event(item)
    }

    fun onLoad(category: Category?) {
        viewModelScope.launch {
            io {
                val result = getItems(category)
                ui {
                    _items.value = result
                }
            }
        }
    }

    private suspend fun getItems(category: Category?): List<ExerciseDataSet>? {
        if (category != null){
            return exerciseProvider.getExerciseDataSetsByCategory(category)
        }
        return exerciseProvider.getExerciseDataSets(true)
    }

}