package es.upsa.mimo.v2021.fitup.ui.register

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.upsa.mimo.v2021.fitup.model.DBEntities.UserItem
import es.upsa.mimo.v2021.fitup.providers.UserProvider
import es.upsa.mimo.v2021.fitup.ui.trainingLists.MessageData
import es.upsa.mimo.v2021.fitup.utils.Constants
import es.upsa.mimo.v2021.fitup.utils.Event
import es.upsa.mimo.v2021.fitup.utils.io
import es.upsa.mimo.v2021.fitup.utils.ui
import kotlinx.coroutines.launch
import java.util.regex.Matcher
import java.util.regex.Pattern


class RegisterViewModel(private val userProvider: UserProvider) : ViewModel() {
    private val _navigateToHome = MutableLiveData<Event<UserItem>>()
    val navigateToHome: LiveData<Event<UserItem>> get() = _navigateToHome

    private val _showMessage = MutableLiveData<Event<MessageData>>()
    val showMessage: LiveData<Event<MessageData>> get() = _showMessage

    fun navigateHome(item: UserItem) {
        _navigateToHome.value = Event(item)
    }

    fun onSubmit(email: String?, password: String?, name: String?, age: Int?) {
        viewModelScope.launch {
            io {
                if (!validUserData(email, password, name, age)) {
                    return@io
                }
                val user = UserItem(email,name,password, age!!,"0")
                if (insertUser(user)) {
                    ui {
                        navigateHome(user)
                    }
                }
            }
        }
    }

    private suspend fun validUserData(email: String?, password: String?, name: String?, age: Int?): Boolean {
        if (email.isNullOrEmpty() || password.isNullOrEmpty() || name.isNullOrEmpty() || age == null) {
            setError("Error", "Fields must be filled")
            return false
        }
        if (age < 12) {
            setError("Error", "You must be at least 12 years old")
            return false
        }
        if (!email.isValidEmail()) {
            setError("Register error", "Email is invalid")
            return false
        }
        if (!password.isValidPassword()) {
            setError("Register error", "Password is invalid")
            return false
        }
        if (userProvider.getUserByEmail(email) != null) {
            setError("Register error", "Email is already in use")
            return false
        }

        return true
    }

    private fun CharSequence.isValidEmail() = Patterns.EMAIL_ADDRESS.matcher(this).matches()

    private fun CharSequence.isValidPassword(): Boolean {
        val pattern: Pattern = Pattern.compile(Constants.PASSWORD_PATTERN)
        val matcher: Matcher = pattern.matcher(this)
        return matcher.matches()
    }

    private suspend fun insertUser(user: UserItem): Boolean {
        return userProvider.insertUser(user)
    }

    private suspend fun setError(title: String, message: String) {
        ui {
            _showMessage.value = Event(MessageData(title, message))
        }
    }
}
