package es.upsa.mimo.v2021.fitup.model

import com.google.gson.annotations.SerializedName

data class Muscles(override val count: Int, override val next: String, override val previous: String,
                   @SerializedName("results") val muscles: List<Exercise>) : Collection(count, next, previous)