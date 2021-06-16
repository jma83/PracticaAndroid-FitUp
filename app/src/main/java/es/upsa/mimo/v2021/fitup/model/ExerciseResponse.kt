package es.upsa.mimo.v2021.fitup.model

import com.google.gson.annotations.SerializedName

//https://wger.de/api/v2/exercise/
data class ExerciseResponse(@SerializedName("exercises") var images: List<String>)
