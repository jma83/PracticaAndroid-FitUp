package es.upsa.mimo.v2021.fitup.model.APIEntities

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface APIService {
    @GET
    fun getExercises(@Url url:String): Call<Exercises>
    @GET
    fun getExercise(@Url url:String): Call<Exercise>
    @GET
    fun getExerciseImages(@Url url:String): Call<ExerciseImages>
    @GET
    fun getCategories(@Url url:String): Call<Categories>
    @GET
    fun getCategory(@Url url:String): Call<Category>
    @GET
    fun getMuscles(@Url url:String): Call<Muscles>
    @GET
    fun getMuscle(@Url url:String): Call<Muscle>
}