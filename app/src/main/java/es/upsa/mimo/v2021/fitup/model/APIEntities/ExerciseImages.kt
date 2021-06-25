package es.upsa.mimo.v2021.fitup.model.APIEntities

import com.google.gson.annotations.SerializedName

class ExerciseImages(@SerializedName("results") val images: List<ExerciseImage>) : Collection() {
}