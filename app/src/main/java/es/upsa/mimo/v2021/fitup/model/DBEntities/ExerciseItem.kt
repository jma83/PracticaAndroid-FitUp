package es.upsa.mimo.v2021.fitup.model.DBEntities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ExerciseItem(@PrimaryKey var id: Int = 0, var external_id: Int, var name: String, var exercise_base: Int,
                        var description: String, var category: Int, var muscles: Array<Int>) {

}