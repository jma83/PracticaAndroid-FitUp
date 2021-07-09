package es.upsa.mimo.v2021.fitup.model.APIEntities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

//https://wger.de/api/v2/exercisecategory/
class Category(val id: Int, val name: String, var image: String?): Serializable