package es.upsa.mimo.v2021.fitup.model.APIEntities

import com.google.gson.annotations.SerializedName

data class Muscles(@SerializedName("results") val muscles: List<Exercise>) : Collection()