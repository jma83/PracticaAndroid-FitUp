package es.upsa.mimo.v2021.fitup.providers

import android.util.Log
import es.upsa.mimo.v2021.fitup.model.DBEntities.ExerciseItem
import es.upsa.mimo.v2021.fitup.persistence.db.FitUpDatabase
import es.upsa.mimo.v2021.fitup.model.DBEntities.TrainingListItem
import es.upsa.mimo.v2021.fitup.model.DBEntities.UserItem
import es.upsa.mimo.v2021.fitup.utils.Constants
import kotlin.Exception

interface TrainingListsProvider {
    suspend fun getTrainingLists(user: UserItem) : List<TrainingListItem>
    suspend fun getTrainingList(id: Int, user: UserItem) : TrainingListItem?
    suspend fun manageExerciseToTrainingList(exerciseItem: ExerciseItem?, trainingListItem: TrainingListItem?, user: UserItem?, isChecked: Boolean): Boolean
    suspend fun deleteTrainingList(trainingListItem: TrainingListItem, user: UserItem): Boolean
}

object TrainingListsProviderImpl: TrainingListsProvider {

    override suspend fun getTrainingLists(user: UserItem): List<TrainingListItem> {
        var result: List<TrainingListItem> = emptyList()
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
        try {
            val result = db.TrainingListDao().getAllByUserAndId(user, id)
            return result
        }catch (e: Exception){
            Log.e(Constants.APP_TAG,"Error retrieving TrainingList: ${e.message}")
        }

        return null
    }

    override suspend fun manageExerciseToTrainingList(exerciseItem: ExerciseItem?, trainingListItem: TrainingListItem?, user: UserItem?, isChecked: Boolean): Boolean  {
        val db = getConnection()
        if (trainingListItem == null || user == null || exerciseItem == null) {
            return false
        }
        if (trainingListItem.userItem?.id != user.id) {
            return false
        }

        val result: TrainingListItem
        if (!isChecked && trainingListItem.exercises!!.contains(exerciseItem)) {
            result = removeFromList(exerciseItem, trainingListItem)
        } else {
            result = addToList(exerciseItem, trainingListItem)
        }
        try {
            db.TrainingListDao().update(result)
            return true
        }catch (e: Exception){
            Log.e(Constants.APP_TAG, "Error updating: ${e.message}")
        }
        return false
    }

    override suspend fun deleteTrainingList(trainingListItem: TrainingListItem, user: UserItem): Boolean {
        val db = getConnection()
        try {
            if (trainingListItem.userItem!!.id != user.id){
                return false
            }
            db.TrainingListDao().delete(trainingListItem)
            return true
        }catch (e: Exception){
            Log.e(Constants.APP_TAG,"Error retrieving TrainingList: ${e.message}")
        }
        return false
    }

    private fun addToList(exerciseItem: ExerciseItem, trainingListItem: TrainingListItem): TrainingListItem {
        if (trainingListItem.exercises == null) {
            trainingListItem.exercises = mutableListOf()
        }
        trainingListItem.exercises!!.add(exerciseItem)
        return trainingListItem
    }

    private fun removeFromList(exerciseItem: ExerciseItem, trainingListItem: TrainingListItem): TrainingListItem {
        if (trainingListItem.exercises == null) {
            trainingListItem.exercises = mutableListOf()
        }
        trainingListItem.exercises!!.remove(exerciseItem)
        return trainingListItem
    }

    private fun getConnection(): FitUpDatabase {
        return FitUpDatabase.get()!!
    }
}