package es.upsa.mimo.v2021.fitup.ui.start

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import es.upsa.mimo.v2021.fitup.utils.Event
import es.upsa.mimo.v2021.fitup.extensions.io
import es.upsa.mimo.v2021.fitup.extensions.ui
import es.upsa.mimo.v2021.fitup.model.DBEntities.UserItem
import es.upsa.mimo.v2021.fitup.persistence.db.FitUpDatabase
import es.upsa.mimo.v2021.fitup.utils.Constants
import kotlinx.coroutines.launch
import java.util.*

class StartViewModel() : ViewModel()  {
    private val _navigateToHome = MutableLiveData<Event<UserItem>>()
    private val _navigateToRegister = MutableLiveData<Event<Boolean>>()
    val navigateToHome: LiveData<Event<UserItem>> get() = _navigateToHome
    val navigateToRegister: LiveData<Event<Boolean>> get() = _navigateToRegister

    fun navigateHome(item: UserItem) {
        _navigateToHome.value = Event(item)
    }

    fun onRegisterClicked() {
        _navigateToRegister.value = Event(true)
    }

    fun onSubmit(email: String, password: String) {
        viewModelScope.launch {
            io {
                if (!validUserData(email, password)){
                    return@io
                }
                val user = UserItem("pepe@pepe.com", "pepe", "password", Date(), "lfkalsfkal")
                if (insertUser(user)) {
                    ui {
                        navigateHome(user)
                    }
                }
            }
        }
    }

    private suspend fun validUserData(email: String, password: String): Boolean{
        var result = true
        if (!result && email.isNotEmpty() && password.isNotEmpty()) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (!it.isSuccessful) {
                    Log.e(Constants.APP_TAG,"An error ocurred during authentication process. Please try again later.")
                }
                result = it.isSuccessful
            }
        }
        return result
    }

    private suspend fun insertUser(user: UserItem): Boolean {
        try {
            FitUpDatabase.get()?.UserDao()?.insert(user)
            return true
        }catch (e: Exception) {
            Log.e(Constants.APP_TAG,"Error inserting user")
        }
        return false
    }

}