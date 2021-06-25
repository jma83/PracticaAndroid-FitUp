package es.upsa.mimo.v2021.fitup.model.APIEntities

import com.google.gson.annotations.SerializedName

//https://wger.de/api/v2/exercise/
data class Exercise(val id: Int, val uuid: String, val name: String, val exercise_base: Int, val status: String,
                    val description: String, val category: Int, val muscles: Array<Int>, val muscles_secondary: Array<Int>,
                    val language: Int, val variations: Array<Int>)
