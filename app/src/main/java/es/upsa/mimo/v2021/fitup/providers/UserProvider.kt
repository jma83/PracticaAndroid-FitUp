package es.upsa.mimo.v2021.fitup.providers

import android.util.Log
import es.upsa.mimo.v2021.fitup.model.DBEntities.UserItem
import es.upsa.mimo.v2021.fitup.persistence.db.FitUpDatabase
import es.upsa.mimo.v2021.fitup.utils.Constants
import java.text.SimpleDateFormat
import java.util.*


interface UserProvider {
    suspend fun getUserByEmail(userEmail: String) : UserItem?
    suspend fun insertUser(userItem: UserItem): Boolean
    suspend fun updateUserToken(userItem: UserItem): Boolean
    suspend fun updateUserPassword(userItem: UserItem, password: String): Boolean
    suspend fun getUserSession(userEmail: String, userToken: String): UserItem?
    suspend fun checkUserByEmailAndPass(userEmail: String, password: String): UserItem?
}

object UserProviderImpl: UserProvider {
    override suspend fun getUserByEmail(userEmail: String) : UserItem? {
        val db = getConnection()
        val user = db.UserDao().getUserByEmail(userEmail)
        return user
    }

    override suspend fun checkUserByEmailAndPass(userEmail: String, password: String): UserItem? {
        val db = getConnection()
        val user = db.UserDao().checkUser(userEmail, password)
        return user
    }

    override suspend fun getUserSession(userEmail: String, userToken: String): UserItem? {
        val db = getConnection()
        val user = db.UserDao().getUserSession(userEmail, userToken)
        return user
    }

    override suspend fun insertUser(userItem: UserItem): Boolean {
        try {
            userItem.userToken = generateUserToken(userItem.email!!)
            getConnection().UserDao().insert(userItem)
            return true
        }catch (e: Exception) {
            Log.e(Constants.APP_TAG,"Error inserting user: $e")
        }
        return false

    }

    override suspend fun updateUserToken(userItem: UserItem): Boolean {
        try {
            userItem.userToken = generateUserToken(userItem.email!!)
            getConnection().UserDao().update(userItem)
            return true
        }catch (e: Exception) {
            Log.e(Constants.APP_TAG,"Error updating user: $e")
        }
        return false
    }

    override suspend fun updateUserPassword(userItem: UserItem, password: String): Boolean {
        try {
            userItem.password = password
            getConnection().UserDao().update(userItem)
            return true
        }catch (e: Exception) {
            Log.e(Constants.APP_TAG,"Error updating user: $e")
        }
        return false
    }

    private fun getConnection(): FitUpDatabase {
        return FitUpDatabase.get()!!
    }

    fun generateUserToken(username: String): String {
        val currentDate: String = SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault()).format(Date())
        val keySource = username + currentDate + Math.random()
        val bytes = android.util.Base64.encode(keySource.toByteArray(), android.util.Base64.DEFAULT)
        return String(bytes).trim()
    }
}