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
import es.upsa.mimo.v2021.fitup.providers.UserProvider
import kotlinx.coroutines.launch

data class TrainingListItemActive(val trainingListItem: TrainingListItem, val active: Boolean)

class AddToTrainingListViewModel(private val trainingListsProvider: TrainingListsProvider, private val userProvider: UserProvider): ViewModel() {
    private val _items = MutableLiveData<List<TrainingListItemActive>>()
    val items: LiveData<List<TrainingListItemActive>> get() = _items
    private var _exerciseItem = MutableLiveData<ExerciseItem>()
    private var _userItem = MutableLiveData<UserItem>()

    fun onAddToListClicked(trainingListItem: TrainingListItemActive, isChecked: Boolean) {
        viewModelScope.launch {
            io {
                manageExercise(trainingListItem, isChecked)
            }
        }
    }

    fun onLoad(userEmail: String?, exerciseItem: ExerciseItem) {
        viewModelScope.launch {
            io {
                val user = userEmail?.let { userProvider.getUserByEmail(it) }
                val result: List<TrainingListItemActive> = getItems(exerciseItem, user)
                ui {
                    _items.value = result
                }
            }
        }
    }

    private suspend fun getItems(exerciseItem: ExerciseItem, user: UserItem?): List<TrainingListItemActive> {
        if (_userItem.value == null){
            return emptyList()
        }
        if (user == null){
            return emptyList()
        }
        val result = trainingListsProvider.getTrainingLists(user)
        if (result == null) {
            return emptyList()
        }
        _exerciseItem.value = exerciseItem
        return result.map {
            TrainingListItemActive(it, it.exercises?.contains(exerciseItem)?: false)
        }
    }

    private suspend fun manageExercise(trainingListItemActive: TrainingListItemActive, isChecked: Boolean){
        trainingListsProvider.manageExerciseToTrainingList(_exerciseItem.value, trainingListItemActive.trainingListItem, _userItem.value, isChecked)
    }
}