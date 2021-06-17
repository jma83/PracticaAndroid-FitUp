package es.upsa.mimo.v2021.fitup.model

import com.google.gson.annotations.SerializedName
import es.upsa.mimo.v2021.fitup.model.Collection

//https://wger.de/api/v2/exercise/
data class Exercises(override val count: Int, override val next: String, override val previous: String,
                     @SerializedName("results") val exercises: List<Exercise>) : Collection(count, next, previous)