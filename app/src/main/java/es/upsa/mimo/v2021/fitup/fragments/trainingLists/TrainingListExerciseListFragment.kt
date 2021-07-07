package es.upsa.mimo.v2021.fitup.fragments.trainingLists

import android.os.Bundle
import android.view.View
import es.upsa.mimo.v2021.fitup.R
import es.upsa.mimo.v2021.fitup.utils.extensions.observe
import es.upsa.mimo.v2021.fitup.fragments.exercises.ExercisesFragment
import es.upsa.mimo.v2021.fitup.model.APIEntities.ExerciseDataSet
import es.upsa.mimo.v2021.fitup.model.DBEntities.TrainingListItem
import es.upsa.mimo.v2021.fitup.ui.exercises.ExerciseAdapter
import es.upsa.mimo.v2021.fitup.ui.trainingLists.exercises.TrainingListsExercisesActivity
import es.upsa.mimo.v2021.fitup.ui.trainingLists.exercises.TrainingListsExercisesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class TrainingListExerciseListFragment: ExercisesFragment() {
    private val viewModel: TrainingListsExercisesViewModel by viewModel()
    val exerciseAdapter by lazy {
        ExerciseAdapter {
            viewModel.onItemClicked(
                it
            )
        }
    }

    companion object {
        fun newInstance(trainingListItem: TrainingListItem? = null): TrainingListExerciseListFragment {
            val exerciseListFragment =
                TrainingListExerciseListFragment()
            val args = Bundle()
            args.putParcelable(TrainingListsExercisesActivity.EXTRA_TRAINING_LIST_ITEM, trainingListItem)
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
        val trainingListItem: TrainingListItem? = getArguments()?.getParcelable<TrainingListItem?>(
            TrainingListsExercisesActivity.EXTRA_TRAINING_LIST_ITEM)
        viewModel.onLoad(trainingListItem)

        setLoading(false)
    }
}