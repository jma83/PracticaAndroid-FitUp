package es.upsa.mimo.v2021.fitup.ui.start

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.upsa.mimo.v2021.fitup.utils.Event
import es.upsa.mimo.v2021.fitup.utils.io
import es.upsa.mimo.v2021.fitup.utils.ui
import es.upsa.mimo.v2021.fitup.model.DBEntities.UserItem
import es.upsa.mimo.v2021.fitup.providers.UserProvider
import es.upsa.mimo.v2021.fitup.ui.trainingLists.MessageData
import kotlinx.coroutines.launch

class StartViewModel(private val userProvider: UserProvider) : ViewModel()  {
    private val _navigateToHome = MutableLiveData<Event<UserItem>>()
    val navigateToHome: LiveData<Event<UserItem>> get() = _navigateToHome
    private val _navigateToRegister = MutableLiveData<Event<Boolean>>()
    val navigateToRegister: LiveData<Event<Boolean>> get() = _navigateToRegister
    private val _showMessage = MutableLiveData<Event<MessageData>>()
    val showMessage: LiveData<Event<MessageData>> get() = _showMessage

    fun navigateHome(item: UserItem) {
        _navigateToHome.value = Event(item)
    }

    fun onRegisterClicked() {
        _navigateToRegister.value = Event(true)
    }

    fun onLoad(email: String?, userToken: String?) {
        viewModelScope.launch {
            io {
                if (email.isNullOrEmpty() || userToken.isNullOrEmpty()){
                    return@io
                }
                val user = userProvider.getUserSession(email, userToken)
                if (user == null) {
                    return@io
                }
                ui {
                    navigateHome(user)
                }
            }
        }
    }

    fun onSubmit(email: String?, password: String?) {
        viewModelScope.launch {
            io {
                val user = validUserData(email, password)
                if (user == null) {
                    return@io
                }
                userProvider.updateUserToken(user)
                ui {
                    navigateHome(user)
                }
            }
        }
    }

    private suspend fun validUserData(email: String?, password: String?): UserItem? {
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            setError("Error", "Fields must be filled")
            return null
        }
        val userResult = userProvider.checkUserByEmailAndPass(email, password)
        if (userResult == null){
            setError("Login error", "Invalid credentials")
            return null
        }
        return userResult
    }

    private suspend fun setError(title: String, message: String) {
        ui {
            _showMessage.value = Event(MessageData(title, message))
        }
    }


}