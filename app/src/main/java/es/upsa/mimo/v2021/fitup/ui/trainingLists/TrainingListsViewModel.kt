package es.upsa.mimo.v2021.fitup.ui.trainingLists

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.upsa.mimo.v2021.fitup.utils.Event
import es.upsa.mimo.v2021.fitup.utils.io
import es.upsa.mimo.v2021.fitup.utils.ui
import es.upsa.mimo.v2021.fitup.model.DBEntities.TrainingListItem
import es.upsa.mimo.v2021.fitup.model.DBEntities.UserItem
import es.upsa.mimo.v2021.fitup.providers.TrainingListsProvider
import es.upsa.mimo.v2021.fitup.providers.UserProvider
import kotlinx.coroutines.launch

class TrainingListsViewModel(private val trainingListsProvider: TrainingListsProvider, private val userProvider: UserProvider): ViewModel() {
    private val _items = MutableLiveData<List<TrainingListItem>>()
    val items: LiveData<List<TrainingListItem>> get() = _items

    private val _navigateToExerciseList = MutableLiveData<Event<TrainingListItem>>()
    val navigateToExerciseList: LiveData<Event<TrainingListItem>> get() = _navigateToExerciseList
    private val _navigateToCreateList = MutableLiveData<Event<Boolean>>()
    val navigateToCreateList: LiveData<Event<Boolean>> get() = _navigateToCreateList
    private var _userItem = MutableLiveData<UserItem>()

    fun onItemClicked(item: TrainingListItem) {
        _navigateToExerciseList.value = Event(item)
    }

    fun onCreateClicked() {
        _navigateToCreateList.value = Event(true)
    }

    fun onLoad(userEmail: String?) {
        viewModelScope.launch {
            io {
                val user = userEmail?.let { userProvider.getUserByEmail(it) }
                if (user == null) return@io
                val result: List<TrainingListItem> = getItems(user)
                ui {
                    _userItem.value = user
                    _items.value = result
                }
            }
        }
    }

    private suspend fun getItems(user: UserItem): List<TrainingListItem> {
        return trainingListsProvider.getTrainingLists(user)
    }

}