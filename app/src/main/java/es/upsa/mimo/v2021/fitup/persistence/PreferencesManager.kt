package es.upsa.mimo.v2021.fitup.persistence

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(private val mContext: Context) {
    private val preferences: SharedPreferences
        get() = mContext.getSharedPreferences(
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

    var darkMode: Boolean
        get() {
            return preferences.getBoolean(DARK_MODE_KEY, false)
        }
        set(darkMode) {
            val edit: SharedPreferences.Editor = preferences.edit()
            edit.putBoolean(DARK_MODE_KEY, darkMode).apply()
        }

    var lockDelete: Boolean
        get() {
            return preferences.getBoolean(LOCK_DELETE_KEY, true)
        }
        set(lockDelete) {
            val edit: SharedPreferences.Editor = preferences.edit()
            edit.putBoolean(LOCK_DELETE_KEY, lockDelete).apply()
        }

    companion object {
        private val SHARED_PREFS_FILE: String = "FitUp_prefs"
        private val USER_EMAIL_KEY: String = "USER_EMAIL_KEY"
        private val USER_TOKEN_KEY: String = "USER_TOKEN_KEY"
        private val DARK_MODE_KEY: String = "DARK_MODE_KEY"
        private val LOCK_DELETE_KEY: String = "LOCK_DELETE_KEY"
    }

}