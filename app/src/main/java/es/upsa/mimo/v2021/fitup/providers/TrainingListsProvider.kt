package es.upsa.mimo.v2021.fitup.providers

import android.util.Log
import es.upsa.mimo.v2021.fitup.model.DBEntities.ExerciseItem
import es.upsa.mimo.v2021.fitup.persistence.db.FitUpDatabase
import es.upsa.mimo.v2021.fitup.model.DBEntities.TrainingListItem
import es.upsa.mimo.v2021.fitup.model.DBEntities.UserItem
import es.upsa.mimo.v2021.fitup.utils.Constants
import java.lang.Exception

interface TrainingListsProvider {
    suspend fun getTrainingLists(user: UserItem) : List<TrainingListItem>?
    suspend fun getTrainingList(id: Int, user: UserItem) : TrainingListItem?
    suspend fun manageExerciseToTrainingList(exerciseItem: ExerciseItem?, trainingListItem: TrainingListItem?, user: UserItem?, isChecked: Boolean): Boolean
}

object TrainingListsProviderImpl: TrainingListsProvider {

    override suspend fun getTrainingLists(user: UserItem): List<TrainingListItem>? {
        var result: List<TrainingListItem>? = emptyList()
        try {
            val db = getConnection()
            result = db.TrainingListDao().getAllByUser(user)
        }catch (e: Exception){
            Log.e(Constants.APP_TAG, "Error when retrieving lists: ${e.message}")
        }
        return result

    }

    override suspend fun getTrainingList(id: Int, user: UserItem): TrainingListItem? {
        val db = getConnection()
        return db.TrainingListDao().getAllByUserAndId(user, id)
    }

    override suspend fun manageExerciseToTrainingList(exerciseItem: ExerciseItem?, trainingListItem: TrainingListItem?, user: UserItem?, isChecked: Boolean): Boolean  {
        val db = getConnection()
        if (trainingListItem == null) {
            return false
        }
        if (user == null || trainingListItem.userItem !== user) {
            return false
        }
        if (exerciseItem == null) {
            return false
        }

        if (!isChecked && trainingListItem.exercises?.contains(exerciseItem)!!) {
            trainingListItem.exercises!!.remove(exerciseItem)
        }else{
            trainingListItem.exercises!!.add(exerciseItem)
        }
        db.TrainingListDao().update(trainingListItem)
        return true
    }

    private fun getConnection(): FitUpDatabase {
        return FitUpDatabase.get()!!
    }
}