package es.upsa.mimo.v2021.fitup.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.upsa.mimo.v2021.fitup.Event
import es.upsa.mimo.v2021.fitup.model.ExerciseDataSet
import es.upsa.mimo.v2021.fitup.providers.ExerciseProvider
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class HomeViewModel (private val exerciseProvider: ExerciseProvider,
                     private val ioDispatcher: CoroutineContext) : ViewModel() {

    private val _items = MutableLiveData<List<ExerciseDataSet>>()
    val items: LiveData<List<ExerciseDataSet>> get() = _items

    private val _navigateToDetail = MutableLiveData<Event<Int>>()
    val navigateToDetail: LiveData<Event<Int>> get() = _navigateToDetail


    fun onItemClicked(item: ExerciseDataSet) {
        _navigateToDetail.value = Event(item.exerciseInfo.id)
    }

    fun onLoad() {
        viewModelScope.launch {
            _items.value = withContext(ioDispatcher) { getItems() }
        }
    }

    private suspend fun getItems(): List<ExerciseDataSet>? {
        return exerciseProvider.getExerciseDataSet()
    }

}