package es.upsa.mimo.v2021.fitup.ui.trainingLists.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.upsa.mimo.v2021.fitup.extensions.io
import es.upsa.mimo.v2021.fitup.extensions.ui
import es.upsa.mimo.v2021.fitup.model.DBEntities.ExerciseItem
import es.upsa.mimo.v2021.fitup.model.DBEntities.TrainingListItem
import es.upsa.mimo.v2021.fitup.model.DBEntities.UserItem
import es.upsa.mimo.v2021.fitup.providers.TrainingListsProvider
import kotlinx.coroutines.launch

class AddToTrainingListViewModel(private val trainingListsProvider: TrainingListsProvider): ViewModel() {
    private val _items = MutableLiveData<List<TrainingListItem>>()
    val items: LiveData<List<TrainingListItem>> get() = _items
    private var _exerciseItem = MutableLiveData<ExerciseItem>()
    private var _userItem = MutableLiveData<UserItem>()

    fun onAddToListClicked(trainingListItem: TrainingListItem) {
        //TODO
        viewModelScope.launch {
            io {
                addExercise(trainingListItem)
            }
        }
    }

    fun onLoad(userEmail: String?) {
        viewModelScope.launch {
            io {
                val result: List<TrainingListItem> = getItems(userEmail)?: emptyList()
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

    private suspend fun addExercise(trainingListItem: TrainingListItem){
        trainingListsProvider.addExerciseToTrainingList(_exerciseItem.value, trainingListItem, _userItem.value)
    }
}