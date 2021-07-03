package es.upsa.mimo.v2021.fitup.persistence

import androidx.room.*
import es.upsa.mimo.v2021.fitup.model.DBEntities.UserItem

interface UserDao {
    @Query("SELECT * FROM UserItem")
    fun getAll(): List<UserItem>?

    @Query("SELECT * FROM UserItem WHERE email = :email")
    fun getUserByEmail(email: String): UserItem?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userItem : UserItem)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(userItem : UserItem)

    @Delete
    fun delete(userItem: UserItem)
}