package es.upsa.mimo.v2021.fitup.providers

import android.util.Log
import es.upsa.mimo.v2021.fitup.model.DBEntities.ExerciseItem
import es.upsa.mimo.v2021.fitup.persistence.db.FitUpDatabase
import es.upsa.mimo.v2021.fitup.model.DBEntities.TrainingListItem
import es.upsa.mimo.v2021.fitup.model.DBEntities.UserItem
import es.upsa.mimo.v2021.fitup.utils.Constants
import java.lang.Exception

interface TrainingListsProvider {
    suspend fun getTrainingLists(userEmail: String) : List<TrainingListItem>?
    suspend fun getTrainingList(id: Int, userEmail: String) : TrainingListItem?
    suspend fun addExerciseToTrainingList(exerciseItem: ExerciseItem?, trainingListItem: TrainingListItem?, user: UserItem?)
}

object TrainingListsProviderImpl: TrainingListsProvider {

    override suspend fun getTrainingLists(userEmail: String): List<TrainingListItem>? {
        var result: List<TrainingListItem>? = emptyList()
        try {
            val db = FitUpDatabase.get()
            val user = db?.UserDao()?.getUserByEmail(userEmail)
            if (user == null) {
                return result
            }
            result = db.TrainingListDao().getAllByUser(user)

        }catch (e: Exception){
            Log.e(Constants.APP_TAG, "Error when retrieving lists: ${e.message}")
        }
        return result

    }

    override suspend fun getTrainingList(id: Int, userEmail: String): TrainingListItem? {
        val db = FitUpDatabase.get()
        val user = db?.UserDao()?.getUserByEmail(userEmail)
        if (user == null) {
            return null
        }
        return db.TrainingListDao().getAllByUserAndId(user, id)
    }

    override suspend fun addExerciseToTrainingList(exerciseItem: ExerciseItem?, trainingListItem: TrainingListItem?, user: UserItem?)  {
        val db = FitUpDatabase.get()
        if (trainingListItem != null) {
            if (trainingListItem.userItem !== user) {
                return
            }
        }
        if (trainingListItem != null) {
            if (exerciseItem != null) {
                trainingListItem.exercises?.add(exerciseItem)
            }
        }
        if (db == null) {
            return
        }
        if (trainingListItem != null) {
            db.TrainingListDao().update(trainingListItem)
        }
    }
}