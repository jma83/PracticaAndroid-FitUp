package es.upsa.mimo.v2021.fitup.providers

import es.upsa.mimo.v2021.fitup.persistence.db.FitUpDatabase
import es.upsa.mimo.v2021.fitup.model.DBEntities.TrainingListItem

interface TrainingListsProvider {
    suspend fun getTrainingLists(userEmail: String) : List<TrainingListItem>?
    suspend fun getTrainingList(id: Int, userEmail: String) : TrainingListItem?
}

object TrainingListsProviderImpl: TrainingListsProvider {

    override suspend fun getTrainingLists(userEmail: String): List<TrainingListItem>? {
        var result: List<TrainingListItem>? = emptyList()
        val db = FitUpDatabase.get()
        val user = db?.UserDao()?.getUserByEmail(userEmail)
        if (user == null) {
            return result
        }
        result = db.TrainingListDao().getAllByUser(user)
        if (result.isEmpty()){
            return result
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
}