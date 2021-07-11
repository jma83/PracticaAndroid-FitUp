package es.upsa.mimo.v2021.fitup.ui.trainingLists.create

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.upsa.mimo.v2021.fitup.utils.Event
import es.upsa.mimo.v2021.fitup.utils.io
import es.upsa.mimo.v2021.fitup.utils.ui
import es.upsa.mimo.v2021.fitup.model.DBEntities.TrainingListItem
import es.upsa.mimo.v2021.fitup.model.DBEntities.UserItem
import es.upsa.mimo.v2021.fitup.persistence.db.FitUpDatabase
import es.upsa.mimo.v2021.fitup.providers.TrainingListsProvider
import es.upsa.mimo.v2021.fitup.providers.UserProvider
import es.upsa.mimo.v2021.fitup.ui.trainingLists.MessageData
import es.upsa.mimo.v2021.fitup.utils.Constants
import kotlinx.coroutines.launch
import java.util.*

class CreateTrainingListViewModel(private val trainingListsProvider: TrainingListsProvider, private val userProvider: UserProvider): ViewModel() {
    private val _navigateToLists = MutableLiveData<Event<Boolean>>()
    val navigateToLists: LiveData<Event<Boolean>> get() = _navigateToLists
    private var currentUser: UserItem? = null
    private val _showMessage = MutableLiveData<Event<MessageData>>()
    val showMessage: LiveData<Event<MessageData>> get() = _showMessage

    fun returnToListsView() {
        _navigateToLists.value = Event(true)
    }

    fun onSubmit(name: String?) {
        viewModelScope.launch {
            io {
                if (!validTrainingList(name)) {
                    return@io
                }
                insertTrainingList(name!!)
                ui {
                    returnToListsView()
                }
            }
        }
    }

    fun onLoad(email: String?, userToken: String?) {
        viewModelScope.launch {
            io {
                if (email.isNullOrEmpty() || userToken.isNullOrEmpty()){
                    return@io
                }
                val user = userProvider.getUserSession(email, userToken)
                ui {
                    currentUser = user
                }
            }
        }
    }

    private suspend fun validTrainingList(name: String?): Boolean{
        if (name.isNullOrEmpty()){
            setMessage("Error creating list", "Name can not be empty or null")
            return false
        }

        if (currentUser == null) {
            setMessage("Error creating list", "Couldn't find user, please try again later.")
            return false
        }

        val lists: List<TrainingListItem> = trainingListsProvider.getTrainingLists(currentUser!!)
        val compareResult =  lists.any  { it.name == name }
        if (compareResult){
            setMessage("Error creating list", "You have already created a list with this name")
            return false
        }
        return true
    }

    private suspend fun insertTrainingList(name: String) {
        val list = TrainingListItem(name, Date(), mutableListOf(),  currentUser!!)
        trainingListsProvider.insertTrainingList(list)
    }

    private suspend fun setMessage(title: String, message: String){
        ui {
            _showMessage.value = Event(MessageData(title, message))
        }
    }


}