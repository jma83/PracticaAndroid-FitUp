package es.upsa.mimo.v2021.fitup.fragments.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.upsa.mimo.v2021.fitup.R
import es.upsa.mimo.v2021.fitup.extensions.observe
import es.upsa.mimo.v2021.fitup.fragments.exercises.ExercisesFragment
import es.upsa.mimo.v2021.fitup.model.APIEntities.Category
import es.upsa.mimo.v2021.fitup.model.APIEntities.ExerciseDataSet
import es.upsa.mimo.v2021.fitup.ui.exercises.CategoryExercisesActivity
import es.upsa.mimo.v2021.fitup.ui.exercises.ExerciseAdapter
import es.upsa.mimo.v2021.fitup.ui.exercises.ExercisesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategoryExerciseListFragment : ExercisesFragment() {
    private val viewModel: ExercisesViewModel by viewModel()
    val exerciseAdapter by lazy {
        ExerciseAdapter {
            viewModel.onItemClicked(
                it
            )
        }
    }

    companion object {
        fun newInstance(category: Category? = null): CategoryExerciseListFragment {
            val exerciseListFragment =
                CategoryExerciseListFragment()
            val args = Bundle()
            args.putSerializable(CategoryExercisesActivity.EXTRA_CATEGORY, category)
            exerciseListFragment.setArguments(args)
            return exerciseListFragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mProgressBar = view.findViewById(R.id.progressBar)
        val detailsFrame: View? = activity?.findViewById(R.id.flExerciseDetail)
        mDualPane = detailsFrame != null && detailsFrame.visibility == View.VISIBLE
        with(viewModel) {
            observe(items) {
                exerciseAdapter.items = it
                val exerciseDataSet: ExerciseDataSet? = it.first()
                if (mDualPane && exerciseDataSet != null) {
                    showDetail(exerciseDataSet)
                }
            }
            observe(navigateToDetail) { event ->
                event.getContentIfNotHandled()?.let { showDetail(it) }
            }
        }
        getView()?.let { setupRecyclerView(it, exerciseAdapter) }
        if (viewModel.items.value != null && viewModel.items.value?.size!! > 0) {
             return
        }
        if (savedInstanceState != null) {
            return
        }

        setLoading(true)
        val category: Category? = getArguments()?.getSerializable(CategoryExercisesActivity.EXTRA_CATEGORY) as Category?
        viewModel.onLoad(category)

        setLoading(false)
    }
}