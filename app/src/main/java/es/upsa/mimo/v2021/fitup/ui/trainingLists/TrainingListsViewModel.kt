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

data class MessageData(val title: String, val message: String) {
}

class TrainingListsViewModel(private val trainingListsProvider: TrainingListsProvider, private val userProvider: UserProvider): ViewModel() {
    private val _items = MutableLiveData<MutableList<TrainingListItem>>()
    val items: LiveData<MutableList<TrainingListItem>> get() = _items

    private val _navigateToExerciseList = MutableLiveData<Event<TrainingListItem>>()
    val navigateToExerciseList: LiveData<Event<TrainingListItem>> get() = _navigateToExerciseList
    private val _navigateToCreateList = MutableLiveData<Event<Boolean>>()
    val navigateToCreateList: LiveData<Event<Boolean>> get() = _navigateToCreateList
    private val _showMessage = MutableLiveData<Event<MessageData>>()
    val showMessage: LiveData<Event<MessageData>> get() = _showMessage
    private var _userItem = MutableLiveData<UserItem>()


    fun onItemClicked(item: TrainingListItem, deleteFlag: Boolean) {
        if (deleteFlag){
            viewModelScope.launch {
                io {
                    if (!deleteItem(item)){
                        return@io
                    }
                    ui {
                        val items = _items.value?.toMutableList()
                        if (items != null) {
                            items.remove(item)
                            _items.value = items
                        }
                    }
                }
            }
            return
        }
        if (item.exercises == null || item.exercises?.isEmpty()!!) {
            _showMessage.value = Event(MessageData("Error", "This Training List has no exercises yet"))
            return
        }
        _navigateToExerciseList.value = Event(item)

    }

    fun onCreateClicked() {
        _navigateToCreateList.value = Event(true)
    }

    fun onLoad(userEmail: String?, userToken: String?) {
        viewModelScope.launch {
            io {
                if (userEmail.isNullOrEmpty() || userToken.isNullOrEmpty()) return@io
                val user = userProvider.getUserSession(userEmail, userToken)
                if (user == null) return@io
                val result: MutableList<TrainingListItem> = getItems(user).toMutableList()
                ui {
                    _userItem.value = user
                    _items.value = result
                }
            }
        }
    }

    private suspend fun deleteItem(trainingListItem: TrainingListItem): Boolean {
        if (!trainingListsProvider.deleteTrainingList(trainingListItem, _userItem.value!!) ){
            setMessage("Error deleting", "Couldn't delete this item. Please, try again")
            return false
        }
        setMessage("Success!", "Training list has been deleted")
        return true

    }

    private suspend fun getItems(user: UserItem): List<TrainingListItem> {
        return trainingListsProvider.getTrainingLists(user)
    }

    private suspend fun setMessage(title: String, message: String){
        ui {
            _showMessage.value = Event(MessageData(title, message))
        }
    }

}