package es.upsa.mimo.v2021.fitup.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.upsa.mimo.v2021.fitup.extensions.io
import es.upsa.mimo.v2021.fitup.extensions.ui
import es.upsa.mimo.v2021.fitup.model.APIEntities.ExerciseDataSet
import es.upsa.mimo.v2021.fitup.providers.ExerciseProvider
import kotlinx.coroutines.launch

class DetailViewModel(private val exerciseProvider: ExerciseProvider): ViewModel() {
    private val _item = MutableLiveData<ExerciseDataSet>()
    val item: LiveData<ExerciseDataSet> get() = _item

    fun onCreate(id: Int) {
        viewModelScope.launch {
            io {
                val result = exerciseProvider.getExerciseDataSet(id)
                ui {
                    _item.value = result
                }
            }
        }
    }
}