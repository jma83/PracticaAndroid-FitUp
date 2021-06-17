package es.upsa.mimo.v2021.fitup.model

import com.google.gson.annotations.SerializedName

class ExerciseImages(override val count: Int, override val next: String, override val previous: String,
                     @SerializedName("results") val images: Array<Category>) : Collection(count, next, previous) {
}