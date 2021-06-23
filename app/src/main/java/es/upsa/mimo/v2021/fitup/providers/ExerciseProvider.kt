package es.upsa.mimo.v2021.fitup.providers

import android.util.Log
import es.upsa.mimo.v2021.fitup.extensions.getRetrofit
import es.upsa.mimo.v2021.fitup.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit

interface ExerciseProvider {
    suspend fun getExercises() : Exercises?
    suspend fun getExerciseImages() : ExerciseImages?
    suspend fun getExerciseImage(exerciseId: Int) : ExerciseImage?
    suspend fun getExerciseDataSet() : List<ExerciseDataSet>?
}

val language = "?language=2"

object ExerciseProviderImpl: ExerciseProvider {
    val numLimit: Int = 10;
    val limit: String = "limit=$numLimit"

    override suspend fun getExercises(): Exercises? = withContext(Dispatchers.IO) {
        val call = Retrofit.Builder().getRetrofit().create(APIService::class.java)
                .getExercises("exercise$language&$limit")
                .execute()
        val exercises: Exercises? = call.body()
        if (!call.isSuccessful) {
            Log.e("upsa.mimo.v2021.fitup", "ERROR! " + call.errorBody())
        }
        return@withContext exercises
    }

    override suspend fun getExerciseImages(): ExerciseImages? = withContext(Dispatchers.IO) {
        val call = Retrofit.Builder().getRetrofit().create(APIService::class.java)
                .getExerciseImages("exerciseImage$language")
                .execute()
        val images: ExerciseImages? = call.body()
        if (!call.isSuccessful) {
            Log.e("upsa.mimo.v2021.fitup", "ERROR! " + call.errorBody())
        }
        return@withContext images
    }

    override suspend fun getExerciseImage(exerciseId: Int): ExerciseImage? = withContext(Dispatchers.IO) {
        val exercise = "exercise_base=$exerciseId"
        val call: Response<ExerciseImages> = Retrofit.Builder().getRetrofit()
            .create(APIService::class.java)
            .getExerciseImages("exerciseimage$language&$exercise")
            .execute()
        val images: ExerciseImages? = call.body()
        if (!call.isSuccessful || images == null) {
            Log.e("upsa.mimo.v2021.fitup", "ERROR! " + call.errorBody())
            return@withContext null
        }
        var image: ExerciseImage? = null;
        if (images!!.images.size > 0) {
            image = images.images.first();
        }

        return@withContext image
    }

    override suspend fun getExerciseDataSet(): List<ExerciseDataSet>? = withContext(Dispatchers.IO) {
        val exercises: Exercises? = getExercises()
        if (exercises == null){
            return@withContext null
        }
        val list:List<ExerciseDataSet> =
            exercises.exercises.map {
                ExerciseDataSet(it, getExerciseImage(it.id))
            }
        return@withContext list
    }

}