package es.upsa.mimo.v2021.fitup.persistence

import androidx.room.*
import es.upsa.mimo.v2021.fitup.model.DBEntities.TrainingListItem
import es.upsa.mimo.v2021.fitup.model.DBEntities.UserItem

interface TrainingListDao {
    @Query("SELECT * FROM TrainingListItem")
    fun getAll(): List<TrainingListDao>

    @Query("SELECT * FROM TrainingListItem WHERE userItem = :userItem")
    fun getAllByUser(userItem: UserItem): List<TrainingListItem>

    @Query("SELECT * FROM TrainingListItem WHERE userItem = :userItem and id = :id")
    fun getAllByUserAndId(userItem: UserItem, id: Int): TrainingListItem?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(trainingListItem : TrainingListItem)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(trainingListItem : TrainingListItem)

    @Delete
    fun delete(trainingListItem: TrainingListItem)
}