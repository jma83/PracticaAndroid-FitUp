package es.upsa.mimo.v2021.fitup.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.upsa.mimo.v2021.fitup.utils.io
import es.upsa.mimo.v2021.fitup.utils.ui
import es.upsa.mimo.v2021.fitup.model.APIEntities.Category
import es.upsa.mimo.v2021.fitup.model.APIEntities.ExerciseDataSet
import es.upsa.mimo.v2021.fitup.model.APIEntities.Muscle
import es.upsa.mimo.v2021.fitup.providers.CategoryProvider
import es.upsa.mimo.v2021.fitup.providers.MuscleProvider
import kotlinx.coroutines.launch

class DetailViewModel(private val musclePrvoider: MuscleProvider, private val categoryProvider: CategoryProvider): ViewModel() {
    private val _item = MutableLiveData<ExerciseDataSet>()
    val item: LiveData<ExerciseDataSet> get() = _item
    private val _category = MutableLiveData<Category>()
    val category: LiveData<Category> get() = _category
    private val _muscles = MutableLiveData<List<Muscle>>()
    val muscles: LiveData<List<Muscle>> get() = _muscles

    fun onCreate(exerciseDataSet: ExerciseDataSet) {
        viewModelScope.launch {
            io {
                val muscleList: List<Muscle>? =
                    musclePrvoider.getMusclesByIds(exerciseDataSet.exerciseInfo.muscles)
                val category: Category? =
                    categoryProvider.getCategory(exerciseDataSet.exerciseInfo.category)
                ui {
                    _item.value = exerciseDataSet
                    _muscles.value = muscleList
                    _category.value = category
                }
            }
        }
    }
}