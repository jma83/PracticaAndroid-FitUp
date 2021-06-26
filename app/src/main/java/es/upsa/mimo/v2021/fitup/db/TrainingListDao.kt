package es.upsa.mimo.v2021.fitup.db

import androidx.room.*
import es.upsa.mimo.v2021.fitup.model.DBEntities.TrainingList

interface TrainingListDao {
    @Query("SELECT * FROM TrainingList")
    fun getAll(): List<TrainingListDao>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(trainingList : TrainingList)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(trainingList : TrainingList)

    @Delete
    fun delete(trainingList: TrainingList)
}