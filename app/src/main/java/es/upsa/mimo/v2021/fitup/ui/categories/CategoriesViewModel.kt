package es.upsa.mimo.v2021.fitup.ui.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.upsa.mimo.v2021.fitup.Event
import es.upsa.mimo.v2021.fitup.extensions.io
import es.upsa.mimo.v2021.fitup.extensions.ui
import es.upsa.mimo.v2021.fitup.model.APIEntities.Categories
import es.upsa.mimo.v2021.fitup.model.APIEntities.Category
import es.upsa.mimo.v2021.fitup.providers.CategoryProvider
import kotlinx.coroutines.launch

class CategoriesViewModel(val categoryProvider: CategoryProvider): ViewModel() {
    private val _items = MutableLiveData<Categories>()
    val items: LiveData<Categories> get() = _items

    private val _navigateToExerciseList = MutableLiveData<Event<Category>>()
    val navigateToExerciseList: LiveData<Event<Category>> get() = _navigateToExerciseList

    fun onItemClicked(item: Category) {
        _navigateToExerciseList.value = Event(item)
    }

    fun onCreate() {
        viewModelScope.launch {
            io {
                val result = getItems()
                ui {
                    _items.value = result
                }
            }
        }
    }

    private suspend fun getItems(): Categories? {
        return categoryProvider.getCategories()
    }
}