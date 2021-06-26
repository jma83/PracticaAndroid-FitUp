package es.upsa.mimo.v2021.fitup.model.DBEntities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TrainingList(@PrimaryKey var id: Int, var user: User, var exercises: List<ExerciseItem>) {

}