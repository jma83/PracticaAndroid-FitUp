package es.upsa.mimo.v2021.fitup.model

import com.google.gson.annotations.SerializedName

//https://wger.de/api/v2/exercisecategory/
data class Categories(override val count: Int, override val next: String, override val previous: String,
                      @SerializedName("results") val categories: Array<Category>) : Collection(count, next, previous)