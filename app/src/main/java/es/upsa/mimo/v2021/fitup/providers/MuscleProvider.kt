package es.upsa.mimo.v2021.fitup.providers

import android.util.Log
import es.upsa.mimo.v2021.fitup.extensions.getRetrofit
import es.upsa.mimo.v2021.fitup.model.APIEntities.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

interface MuscleProvider {
        suspend fun getMuscle(id: Int) : Muscle?
        suspend fun getMusclesByIds(ids: List<Int>) : List<Muscle>?
        suspend fun getMuscles() : Muscles?
}

object MuscleProviderImpl: MuscleProvider {
    val language = "?language=2"

    override suspend fun getMuscle(id: Int) : Muscle? = withContext(Dispatchers.IO) {
        val idParam = "/$id"
        val call = Retrofit.Builder().getRetrofit().create(APIService::class.java)
            .getMuscle("muscle$idParam$language")
            .execute()
        val muscle: Muscle? = call.body()

        return@withContext muscle
    }

    override suspend fun getMusclesByIds(ids: List<Int>) : List<Muscle>? = withContext(Dispatchers.IO) {
        val muscles: Muscles? = getMuscles()
        val muscleList: List<Muscle>? =  muscles?.muscles?.filter {
            it.id in ids
        }

        return@withContext muscleList
    }

    override suspend fun getMuscles() : Muscles? = withContext(Dispatchers.IO) {
        val call = Retrofit.Builder().getRetrofit().create(APIService::class.java)
            .getMuscles("muscle$language")
            .execute()
        val muscles: Muscles? = call.body()
        if (!call.isSuccessful) {
            Log.e("upsa.mimo.v2021.fitup", "ERROR! " + call.errorBody().toString())
        }
        return@withContext muscles
    }
}