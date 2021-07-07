package es.upsa.mimo.v2021.fitup.providers

import android.util.Log
import es.upsa.mimo.v2021.fitup.utils.extensions.getRetrofit
import es.upsa.mimo.v2021.fitup.model.APIEntities.*
import es.upsa.mimo.v2021.fitup.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

interface CategoryProvider {
    suspend fun getCategory(id: Int) : Category?
    suspend fun getCategoriesByIds(ids: List<Int>) : List<Category>?
    suspend fun getCategories() : Categories?
}

object CategoryProviderImpl: CategoryProvider {
    val language = "?language=2"
    val route = "exercisecategory"
    override suspend fun getCategory(id: Int) : Category? = withContext(Dispatchers.IO) {
        val idParam = "/$id"
        val call = Retrofit.Builder().getRetrofit().create(APIService::class.java)
            .getCategory("$route$idParam$language")
            .execute()
        val category: Category? = call.body()

        return@withContext category
    }

    override suspend fun getCategoriesByIds(ids: List<Int>) : List<Category>? = withContext(Dispatchers.IO) {
        val categories: Categories? = getCategories()
        val categoryList: List<Category>? =  categories?.categories?.filter {
            it.id in ids
        }

        return@withContext categoryList
    }

    override suspend fun getCategories() : Categories? = withContext(Dispatchers.IO) {
        val call = Retrofit.Builder().getRetrofit().create(APIService::class.java)
            .getCategories("$route$language")
            .execute()
        val categories: Categories? = call.body()
        if (!call.isSuccessful) {
            Log.e(Constants.APP_TAG, "ERROR! " + call.errorBody())
        }
        return@withContext categories
    }
}