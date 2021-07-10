package es.upsa.mimo.v2021.fitup.ui.settings

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

class SettingsViewModel(private val userProvider: UserProvider): ViewModel() {
    private val _showUserInfo = MutableLiveData<Event<UserItem>>()
    val showUserInfo: LiveData<Event<UserItem>> get() = _showUserInfo
    private val _navigateToLogin = MutableLiveData<Event<Boolean>>()
    val navigateToLogin: LiveData<Event<Boolean>> get() = _navigateToLogin
    private val _showMessage = MutableLiveData<Event<MessageData>>()
    val showMessage: LiveData<Event<MessageData>> get() = _showMessage
    private var _userItem: UserItem? = null


    fun showUserInfo(item: UserItem) {
        _userItem = item
        _showUserInfo.value = Event(item)
    }

    fun navigateToLogin() {
        _navigateToLogin.value = Event(true)
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
                    showUserInfo(user)
                }
            }
        }
    }

    fun onSubmit(oldPassword: String?, newPassword: String?) {
        viewModelScope.launch {
            io {
                if (!validateData(oldPassword, newPassword)){
                    return@io
                }
                val user = userProvider.checkUserByEmailAndPass(_userItem!!.email!!, oldPassword!!)
                if (user == null) {
                    setMessage("Change password error", "Current password does not match")
                    return@io
                }
                userProvider.updateUserPassword(user, newPassword!!)
                setMessage("Success!", "Your password has been updated")
                ui {
                    showUserInfo(user)
                }
            }
        }
    }

    private suspend fun validateData(oldPassword: String?, newPassword: String?): Boolean{
        if (oldPassword.isNullOrEmpty() || newPassword.isNullOrEmpty() || _userItem == null){
            setMessage("Change password error", "Complete both fields")
            return false
        }
        if (oldPassword == newPassword){
            setMessage("Change password error", "Passwords must be different")
            return false
        }
        if (!newPassword.isValidPassword()){
            setMessage("Change password error", "The new password is not valid")
            return false
        }

        return true
    }

    private fun CharSequence.isValidPassword(): Boolean {
        val pattern: Pattern = Pattern.compile(Constants.PASSWORD_PATTERN)
        val matcher: Matcher = pattern.matcher(this)
        return matcher.matches()
    }

    private suspend fun setMessage(title: String, message: String) {
        ui {
            _showMessage.value = Event(MessageData(title, message))
        }
    }
}