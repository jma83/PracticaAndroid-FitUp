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
}

object UserProviderImpl: UserProvider {
    override suspend fun getUserByEmail(userEmail: String) : UserItem? {
        val db = getConnection()
        val user = db.UserDao().getUserByEmail(userEmail)
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

    private fun getConnection(): FitUpDatabase {
        return FitUpDatabase.get()!!
    }

    fun generateUserToken(username: String): String {
        val currentDate: String = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
        val keySource = username + currentDate + Math.random()
        val bytes = android.util.Base64.encode(keySource.toByteArray(), android.util.Base64.DEFAULT)
        return String(bytes)
    }
}


/*

val db = FitUpDatabase.get()
            val user = db?.UserDao()?.getUserByEmail(userEmail)
            if (user == null) {
                return result
            }
 */