package es.upsa.mimo.v2021.fitup.providers

import android.util.Log
import es.upsa.mimo.v2021.fitup.extensions.getRetrofit
import es.upsa.mimo.v2021.fitup.model.APIEntities.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import kotlin.random.Random

interface ExerciseProvider {
    suspend fun getExercise(exerciseId: Int) : Exercise?
    suspend fun getExercises(random: Boolean? = false, offset: Int? = 0, limit: Int? = 10) : Exercises?
    suspend fun getExercisesByCategory(category: Category?, offset: Int? = 0, limit: Int? = 10) : Exercises?
    suspend fun getExerciseImage(exerciseId: Int) : ExerciseImage?
    suspend fun getExerciseImages() : ExerciseImages?
    suspend fun getExerciseDataSetFromExercises(exercises: List<Exercise>?) : List<ExerciseDataSet>?
    suspend fun getExerciseDataSet(exerciseId: Int) : ExerciseDataSet?
    suspend fun getExerciseDataSets(random: Boolean? = false, offset: Int? = 0, limit: Int? = 10) : List<ExerciseDataSet>?
    suspend fun getExerciseDataSetsByCategory(category: Category?, offset: Int? = 0, limit: Int? = 10) : List<ExerciseDataSet>?
}


object ExerciseProviderImpl: ExerciseProvider {
    val limitParam: String = "limit="
    val offsetParam: String = "offset="
    val categoryParam: String = "category="
    val exerciseBaseParam: String = "exercise_base="
    val language = "?language=2"
    val route = "exercise"
    val routeImage = "exerciseimage"
    val maxOffset = 228

    override suspend fun getExercisesByCategory(category: Category?, offset: Int?, limit: Int?): Exercises? = withContext(Dispatchers.IO) {
        var endpointRoute = "$route$language&$limitParam$limit&$offsetParam$offset"
        if (category != null){
            endpointRoute+="&$categoryParam${category.id}"
        }
        return@withContext getExercisesResult(endpointRoute)
    }

    override suspend fun getExercises(random: Boolean?, offset: Int?, limit: Int?): Exercises? = withContext(Dispatchers.IO) {
        var finalOffset = offset
        if (random == true){
            finalOffset = Random.nextInt(0, maxOffset)
        }
        val endpointRoute = "$route$language&$limitParam$limit&$offsetParam$finalOffset"

        return@withContext getExercisesResult(endpointRoute)
    }

    override suspend fun getExercise(exerciseId: Int): Exercise? = withContext(Dispatchers.IO) {
        val call = Retrofit.Builder().getRetrofit().create(APIService::class.java)
            .getExercise("$route/$exerciseId$language")
            .execute()
        val exercise: Exercise? = call.body()
        if (!call.isSuccessful) {
            Log.e("upsa.mimo.v2021.fitup", "ERROR!2 " + call.errorBody().toString())
        }
        return@withContext exercise
    }

    override suspend fun getExerciseImages(): ExerciseImages? = withContext(Dispatchers.IO) {
        val call = Retrofit.Builder().getRetrofit().create(APIService::class.java)
                .getExerciseImages("$routeImage$language")
                .execute()
        val images: ExerciseImages? = call.body()
        if (!call.isSuccessful) {
            Log.e("upsa.mimo.v2021.fitup", "ERROR!3 " + call.errorBody().toString())
        }
        return@withContext images
    }

    override suspend fun getExerciseImage(exerciseId: Int): ExerciseImage? = withContext(Dispatchers.IO) {
        val call: Response<ExerciseImages> = Retrofit.Builder().getRetrofit()
            .create(APIService::class.java)
            .getExerciseImages("$routeImage$language&$exerciseBaseParam$exerciseId")
            .execute()
        val images: ExerciseImages? = call.body()
        if (!call.isSuccessful) {
            Log.e("upsa.mimo.v2021.fitup", "ERROR!4 " + call.errorBody().toString())
            return@withContext null
        }
        var image: ExerciseImage? = null;
        if (images != null && images.images.size > 0) {
            Log.e("upsa.mimo.v2021.fitup", "se deberia de ver una imagen!!!")
            image = images.images.first();
        }

        return@withContext image
    }

    override suspend fun getExerciseDataSetFromExercises(exercises: List<Exercise>?) : List<ExerciseDataSet>? {
        return exercises?.map { ExerciseDataSet(it, getExerciseImage(it.id)) }
    }

    override suspend fun getExerciseDataSet(exerciseId: Int): ExerciseDataSet? = withContext(Dispatchers.IO) {
        val exercise: Exercise? = getExercise(exerciseId)
        if (exercise == null){
            return@withContext null
        }
        return@withContext ExerciseDataSet(exercise, getExerciseImage(exerciseId))
    }

    override suspend fun getExerciseDataSets(random: Boolean?, offset: Int?, limit: Int?): List<ExerciseDataSet>? = withContext(Dispatchers.IO) {
        val exercises: Exercises? = getExercises(random, offset, limit)
        return@withContext getExerciseDataSetResult(exercises)
    }

    override suspend fun getExerciseDataSetsByCategory(category: Category?, offset: Int?, limit: Int?): List<ExerciseDataSet>? = withContext(Dispatchers.IO) {
        val exercises: Exercises? = getExercisesByCategory(category, offset, limit)
        return@withContext getExerciseDataSetResult(exercises)
    }


    private suspend fun getExerciseDataSetResult(exercises: Exercises?): List<ExerciseDataSet>? = withContext(Dispatchers.IO) {
        if (exercises == null){
            return@withContext null
        }
        return@withContext exercises.exercises.map {
            ExerciseDataSet(it, getExerciseImage(it.id))
        }
    }

    private suspend fun getExercisesResult(endpointRoute: String): Exercises? = withContext(Dispatchers.IO) {
        val call = Retrofit.Builder().getRetrofit().create(APIService::class.java)
            .getExercises(endpointRoute)
            .execute()
        val exercises: Exercises? = call.body()
        if (!call.isSuccessful) {
            Log.e("upsa.mimo.v2021.fitup", "ERROR!1 " + call.errorBody().toString())
        }
        return@withContext exercises
    }

}