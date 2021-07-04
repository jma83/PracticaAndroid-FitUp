package es.upsa.mimo.v2021.fitup.ui.trainingLists.create

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.upsa.mimo.v2021.fitup.Event
import es.upsa.mimo.v2021.fitup.extensions.io
import es.upsa.mimo.v2021.fitup.extensions.ui
import es.upsa.mimo.v2021.fitup.model.DBEntities.ExerciseItem
import es.upsa.mimo.v2021.fitup.model.DBEntities.TrainingListItem
import es.upsa.mimo.v2021.fitup.model.DBEntities.UserItem
import es.upsa.mimo.v2021.fitup.persistence.db.FitUpDatabase
import es.upsa.mimo.v2021.fitup.utils.Constants
import kotlinx.coroutines.launch
import java.util.*

class CreateTrainingListViewModel: ViewModel() {
    private val _navigateToLists = MutableLiveData<Event<Boolean>>()
    val navigateToLists: LiveData<Event<Boolean>> get() = _navigateToLists
    private var currentUser: UserItem? = null
    fun returnToListsView() {
        _navigateToLists.value = Event(true)
    }

    fun onSubmit(name: String, userEmail: String) {
        viewModelScope.launch {
            io {
                if (!validTrainingList(name,userEmail)){
                    return@io
                }
                insertTrainingList(name)
                ui {
                    returnToListsView()
                }
            }
        }
    }

    private fun validTrainingList(name: String, userEmail: String): Boolean{
        var checkResult = false
        try {
            if (name.length == 0){
                return false
            }
            if (currentUser == null) {
                currentUser = FitUpDatabase.get()?.UserDao()?.getUserByEmail(userEmail)
                if (currentUser == null) {
                    return false
                }
            }
            val lists: List<TrainingListItem>? = FitUpDatabase.get()?.TrainingListDao()?.getAllByUser(currentUser!!)
            if (lists != null) {
                checkResult = !lists.any  { it.name == name }
            }
        }catch (e: Exception) {
            Log.e(Constants.APP_TAG,"Error validating list")
            Log.e(Constants.APP_TAG,"${e.message}")
        }
        return checkResult
    }

    private suspend fun insertTrainingList(name: String) {
        val list = TrainingListItem(name, Date(), currentUser!!, emptyList<ExerciseItem>())

        FitUpDatabase.get()?.TrainingListDao()?.insert(list)
        //val lists: List<TrainingListItem>? = FitUpDatabase.get()?.TrainingListDao()?.getAllByUser(currentUser!!)
        //Log.i(Constants.APP_TAG, lists.toString())
    }


}