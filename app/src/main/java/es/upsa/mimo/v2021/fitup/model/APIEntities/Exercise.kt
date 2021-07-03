package es.upsa.mimo.v2021.fitup.model.APIEntities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

//https://wger.de/api/v2/exercise/
data class Exercise(val id: Int, val name: String, val exercise_base: Int,
                    val description: String, val category: Int, val muscles: List<Int>,
                    val language: Int) : Serializable
