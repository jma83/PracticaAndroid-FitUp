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
import es.upsa.mimo.v2021.fitup.extensions.observe
import es.upsa.mimo.v2021.fitup.extensions.startActivity1
import es.upsa.mimo.v2021.fitup.model.APIEntities.Category
import es.upsa.mimo.v2021.fitup.ui.categories.CategoriesViewModel
import es.upsa.mimo.v2021.fitup.ui.categories.CategoryAdapter
import es.upsa.mimo.v2021.fitup.ui.exercises.ExercisesActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategoryListFragment: Fragment()  {
    private var mProgressBar: ProgressBar? = null
    private var mRecyclerView: RecyclerView? = null
    private val viewModel: CategoriesViewModel by viewModel()
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
                categoryAdapter.items = it.categories
            }
            observe(navigateToExerciseList) { event ->
                event.getContentIfNotHandled()?.let { showExerciseList(it) }
            }
        }
        getView()?.let { setupRecyclerView(it) }
        if (viewModel.items.value != null && viewModel.items.value?.categories != null && viewModel.items.value?.categories?.size!! > 0) {
            return
        }
        if (savedInstanceState != null) {
            return
        }

        setLoading(true)
        viewModel.onCreate()
        setLoading(false)
    }

    private fun setupRecyclerView(view: View) {
        mRecyclerView = view.findViewById(R.id.recyclerCategoryList)
        if (mRecyclerView != null ) {
            mRecyclerView!!.adapter = categoryAdapter
            mRecyclerView!!.setItemAnimator(DefaultItemAnimator())
        }
    }

    private fun setLoading(loading: Boolean) {
        if (loading) {
            mProgressBar!!.visibility = View.VISIBLE
        } else {
            mProgressBar!!.visibility = View.GONE
        }
    }

    private fun showExerciseList(category: Category) {
        activity?.startActivity1<ExercisesActivity>(ExercisesActivity.EXTRA_CATEGORY to category)
    }
}