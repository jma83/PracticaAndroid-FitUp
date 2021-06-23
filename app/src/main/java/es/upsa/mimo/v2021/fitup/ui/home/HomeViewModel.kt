package es.upsa.mimo.v2021.fitup.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.upsa.mimo.v2021.fitup.Event
import es.upsa.mimo.v2021.fitup.extensions.io
import es.upsa.mimo.v2021.fitup.extensions.ui
import es.upsa.mimo.v2021.fitup.model.ExerciseDataSet
import es.upsa.mimo.v2021.fitup.providers.ExerciseProvider
import kotlinx.coroutines.launch

class HomeViewModel(private val exerciseProvider: ExerciseProvider) : ViewModel() {

    private val _items = MutableLiveData<List<ExerciseDataSet>>()
    val items: LiveData<List<ExerciseDataSet>> get() = _items

    private val _navigateToDetail = MutableLiveData<Event<Int>>()
    val navigateToDetail: LiveData<Event<Int>> get() = _navigateToDetail


    fun onItemClicked(item: ExerciseDataSet) {
        _navigateToDetail.value = Event(item.exerciseInfo.id)
    }

    fun onLoad() {
        viewModelScope.launch {
            io {
                val result = getItems()
                ui {
                    _items.value = result
                }
            }
        }
    }

    private suspend fun getItems(): List<ExerciseDataSet>? {
        return exerciseProvider.getExerciseDataSets()
    }

}