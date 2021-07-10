package es.upsa.mimo.v2021.fitup.fragments.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import es.upsa.mimo.v2021.fitup.R
import es.upsa.mimo.v2021.fitup.fragments.CommonRecyclerFragment
import es.upsa.mimo.v2021.fitup.utils.extensions.observe
import es.upsa.mimo.v2021.fitup.utils.extensions.startNewActivity
import es.upsa.mimo.v2021.fitup.model.APIEntities.Category
import es.upsa.mimo.v2021.fitup.ui.categories.CategoriesViewModel
import es.upsa.mimo.v2021.fitup.ui.categories.CategoryAdapter
import es.upsa.mimo.v2021.fitup.ui.categories.CategoryExercisesActivity
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategoryListFragment: CommonRecyclerFragment()  {
    private val viewModel: CategoriesViewModel by sharedViewModel()
    val categoryAdapter by lazy {
        CategoryAdapter {
            viewModel.onItemClicked(
                it
            )
        }
    }

    companion object {
        fun newInstance(): CategoryListFragment {
            return CategoryListFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_category_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mProgressBar = view.findViewById(R.id.progressBar)
        with(viewModel) {
            observe(items) {
                setLoading(false)
                categoryAdapter.items = it.categories
            }
            observe(navigateToExerciseList) { event ->
                event.getContentIfNotHandled()?.let { showExerciseList(it) }
            }
        }
        getView()?.let { setupRecyclerView(it, categoryAdapter, R.id.recyclerCategoryList) }
        if (viewModel.items.value != null && viewModel.items.value?.categories != null && viewModel.items.value?.categories?.size!! > 0) {
            return
        }
        if (savedInstanceState != null) {
            return
        }

        setLoading(true)
        viewModel.onCreate()
    }

    private fun showExerciseList(category: Category) {
        activity?.startNewActivity<CategoryExercisesActivity>(
            CategoryExercisesActivity.EXTRA_CATEGORY to category)
    }
}