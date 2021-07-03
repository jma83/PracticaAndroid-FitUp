package es.upsa.mimo.v2021.fitup.ui.trainingLists

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.upsa.mimo.v2021.fitup.Event
import es.upsa.mimo.v2021.fitup.extensions.io
import es.upsa.mimo.v2021.fitup.extensions.ui
import es.upsa.mimo.v2021.fitup.model.DBEntities.TrainingListItem
import es.upsa.mimo.v2021.fitup.model.DBEntities.UserItem
import es.upsa.mimo.v2021.fitup.providers.TrainingListsProvider
import kotlinx.coroutines.launch

class TrainingListsViewModel(private val trainingListsProvider: TrainingListsProvider): ViewModel() {
    private val _items = MutableLiveData<List<TrainingListItem>>()
    val items: LiveData<List<TrainingListItem>> get() = _items

    private val _navigateToExerciseList = MutableLiveData<Event<TrainingListItem>>()
    val navigateToExerciseList: LiveData<Event<TrainingListItem>> get() = _navigateToExerciseList

    fun onItemClicked(item: TrainingListItem) {
        _navigateToExerciseList.value = Event(item)
    }

    fun onCreate(userEmail: String?) {
        viewModelScope.launch {
            io {
                val result = getItems(userEmail)
                ui {
                    _items.value = result
                }
            }
        }
    }

    private suspend fun getItems(userEmail: String?): List<TrainingListItem>? {
        if (userEmail == null){
            return null
        }
        return trainingListsProvider.getTrainingLists(userEmail)
    }

}