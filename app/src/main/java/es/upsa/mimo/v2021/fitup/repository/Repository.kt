package es.upsa.mimo.v2021.fitup.repository

import androidx.lifecycle.LiveData


interface Repository {
    fun getExercises() : LiveData<List<*>>

}