package es.upsa.mimo.v2021.fitup.providers

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import es.upsa.mimo.v2021.fitup.extensions.getRetrofit
import es.upsa.mimo.v2021.fitup.extensions.toast
import es.upsa.mimo.v2021.fitup.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import java.util.concurrent.Executor
import java.util.concurrent.Executors

interface ExerciseProvider {
    suspend fun getExercises() : Exercises?
    suspend fun getExerciseImages() : ExerciseImages?
    suspend fun getExerciseDataSet() : List<ExerciseDataSet>?
}

object ExerciseProviderImpl: ExerciseProvider {
    val numLimit: Int = 10;
    val limit: String = "limit=$numLimit"

    override suspend fun getExercises(): Exercises? = withContext(Dispatchers.IO) {
        val call = Retrofit.Builder().getRetrofit().create(APIService::class.java)
                .getExercises("exercise?$limit")
                .execute()
        val exercises: Exercises? = call.body()
        if (!call.isSuccessful) {
            Log.e("upsa.mimo.v2021.fitup", "ERROR! " + call.errorBody())
        }
        return@withContext exercises
    }

    override suspend fun getExerciseImages(): ExerciseImages? = withContext(Dispatchers.IO) {
        val call = Retrofit.Builder().getRetrofit().create(APIService::class.java)
                .getExerciseImages("exerciseImage")
                .execute()
        val images: ExerciseImages? = call.body()
        if (!call.isSuccessful) {
            Log.e("upsa.mimo.v2021.fitup", "ERROR! " + call.errorBody())
        }
        return@withContext images
    }

    override suspend fun getExerciseDataSet(): List<ExerciseDataSet>? = withContext(Dispatchers.IO) {
        val images: ExerciseImages? = getExerciseImages()
        val exercises: Exercises? = getExercises()
        if (images == null || exercises == null){
            return@withContext null
        }
        val list:List<ExerciseDataSet> = images.images.flatMap { image: ExerciseImage ->
            exercises.exercises.filter{
               (it.id == image.exercise_base)
            }.map {
                ExerciseDataSet(it,image)
            }
        }
        return@withContext list
    }

}