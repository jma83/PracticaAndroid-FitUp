package es.upsa.mimo.v2021.fitup.repository.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import es.upsa.mimo.v2021.fitup.model.Exercise
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class RepositoryImpl {
    var executor: Executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())

    fun getCatalog(): LiveData<List<*>>? {
        val exercises = MutableLiveData<List<*>>()
        executor.execute { exercises.postValue(retrieveExercises()) }
        return exercises
    }

    fun retrieveExercises(): List<Exercise> {
        // val res = List<Exercise>()

    }
}