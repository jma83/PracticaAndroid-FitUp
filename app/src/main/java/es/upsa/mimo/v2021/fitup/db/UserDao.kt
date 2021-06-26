package es.upsa.mimo.v2021.fitup.db

import androidx.room.*
import es.upsa.mimo.v2021.fitup.model.DBEntities.User

interface UserDao {
    @Query("SELECT * FROM User")
    fun getAll(): List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user : User)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(user : User)

    @Delete
    fun delete(user: User)
}