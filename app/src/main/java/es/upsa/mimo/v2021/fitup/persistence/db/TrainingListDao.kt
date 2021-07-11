package es.upsa.mimo.v2021.fitup.persistence.db

import androidx.room.*
import es.upsa.mimo.v2021.fitup.model.DBEntities.TrainingListItem
import es.upsa.mimo.v2021.fitup.model.DBEntities.UserItem

@Dao
interface TrainingListDao {
    @Query("SELECT * FROM TrainingListItem")
    fun getAll(): List<TrainingListItem>

    @Query("SELECT * FROM TrainingListItem WHERE userId = :userItem")
    fun getAllByUser(userItem: Int): List<TrainingListItem>

    @Query("SELECT * FROM TrainingListItem WHERE userId = :userItem and id = :id")
    fun getAllByUserAndId(userItem: Int, id: Int): TrainingListItem

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(trainingListItem : TrainingListItem)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(trainingListItem : TrainingListItem)

    @Delete
    fun delete(trainingListItem: TrainingListItem)
}