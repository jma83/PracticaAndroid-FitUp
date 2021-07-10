package es.upsa.mimo.v2021.fitup.persistence.db

import androidx.room.*
import es.upsa.mimo.v2021.fitup.model.DBEntities.UserItem

@Dao
interface UserDao {
    @Query("SELECT * FROM UserItem")
    fun getAll(): List<UserItem>?

    @Query("SELECT * FROM UserItem WHERE email = :email")
    fun getUserByEmail(email: String): UserItem?

    @Query("SELECT * FROM UserItem WHERE email = :email and userToken = :userToken")
    fun getUserSession(email: String, userToken: String): UserItem?

    @Query("SELECT * FROM UserItem WHERE email = :email and password = :password")
    fun checkUser(email: String, password: String): UserItem?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userItem : UserItem)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(userItem : UserItem)

    @Delete
    fun delete(userItem: UserItem)
}