package es.upsa.mimo.v2021.fitup.persistence.db

import androidx.room.*
import es.upsa.mimo.v2021.fitup.model.DBEntities.ExerciseItem

@Dao
interface ExerciseItemsDao {
    @Query("SELECT * FROM ExerciseItem")
    fun getAll(): List<ExerciseItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(exerciseItem : ExerciseItem)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(exerciseItem : ExerciseItem)

    @Delete
    fun delete(exerciseItem: ExerciseItem)
}