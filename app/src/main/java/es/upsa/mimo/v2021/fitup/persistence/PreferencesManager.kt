package es.upsa.mimo.v2021.fitup.persistence

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(private val mContext: Context) {
    private val preferences: SharedPreferences
        private get() = mContext.getSharedPreferences(
            SHARED_PREFS_FILE,
            Context.MODE_PRIVATE
        )

    var email: String?
        get() {
            return preferences.getString(USER_EMAIL_KEY, null)
        }
        set(email) {
            val edit: SharedPreferences.Editor = preferences.edit()
            edit.putString(USER_EMAIL_KEY, email).apply()
        }
    var userToken: String?
        get() {
            return preferences.getString(USER_TOKEN_KEY, null)
        }
        set(userToken) {
            val edit: SharedPreferences.Editor = preferences.edit()
            edit.putString(USER_TOKEN_KEY, userToken).apply()
        }

    companion object {
        private val SHARED_PREFS_FILE: String = "FitUp_prefs"
        private val USER_EMAIL_KEY: String = "USER_EMAIL_KEY"
        private val USER_TOKEN_KEY: String = "USER_TOKEN_KEY"
    }

}