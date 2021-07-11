package es.upsa.mimo.v2021.fitup.fragments.categories

import android.os.Bundle
import android.view.View
import es.upsa.mimo.v2021.fitup.R
import es.upsa.mimo.v2021.fitup.databinding.FragmentExerciseListBinding
import es.upsa.mimo.v2021.fitup.utils.extensions.observe
import es.upsa.mimo.v2021.fitup.fragments.exercises.ExercisesFragment
import es.upsa.mimo.v2021.fitup.model.APIEntities.Category
import es.upsa.mimo.v2021.fitup.model.APIEntities.ExerciseDataSet
import es.upsa.mimo.v2021.fitup.ui.categories.CategoryExercisesActivity
import es.upsa.mimo.v2021.fitup.ui.categories.CategoryExercisesViewModel
import es.upsa.mimo.v2021.fitup.ui.exercises.ExerciseAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CategoryExerciseListFragment : ExercisesFragment() {
    private val viewModel: CategoryExercisesViewModel by sharedViewModel()
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
        val binding = FragmentExerciseListBinding.bind(view)

        mProgressBar = binding.progressBar
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
        getView()?.let { setupRecyclerView(it, exerciseAdapter, R.id.recyclerExerciseList) }
        if (viewModel.items.value != null && viewModel.items.value?.size!! > 0) {
             return
        }
        if (savedInstanceState != null) {
            return
        }

        setLoading(true)
        val category: Category? = getArguments()?.getSerializable(CategoryExercisesActivity.EXTRA_CATEGORY) as Category?
        binding.headerText.text = "${category?.name} exercises:"
        viewModel.onLoad(category)

        setLoading(false)
    }
}