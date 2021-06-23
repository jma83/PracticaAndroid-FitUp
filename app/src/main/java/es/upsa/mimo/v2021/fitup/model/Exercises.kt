package es.upsa.mimo.v2021.fitup.model

import com.google.gson.annotations.SerializedName
import es.upsa.mimo.v2021.fitup.model.Collection

//https://wger.de/api/v2/exercise/
data class Exercises(@SerializedName("results") val exercises: List<Exercise>) : Collection()