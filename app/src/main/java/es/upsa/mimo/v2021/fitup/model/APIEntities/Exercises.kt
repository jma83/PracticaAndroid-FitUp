package es.upsa.mimo.v2021.fitup.model.APIEntities

import com.google.gson.annotations.SerializedName

//https://wger.de/api/v2/exercise/
data class Exercises(@SerializedName("results") val exercises: List<Exercise>) : Collection()