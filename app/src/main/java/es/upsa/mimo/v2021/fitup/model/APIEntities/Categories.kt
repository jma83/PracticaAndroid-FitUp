package es.upsa.mimo.v2021.fitup.model.APIEntities

import com.google.gson.annotations.SerializedName
import es.upsa.mimo.v2021.fitup.model.APIEntities.Category
import es.upsa.mimo.v2021.fitup.model.APIEntities.Collection

//https://wger.de/api/v2/exercisecategory/
data class Categories(@SerializedName("results") val categories: List<Category>) : Collection()