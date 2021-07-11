package es.upsa.mimo.v2021.fitup.fragments.trainingLists

import android.os.Bundle
import android.view.View
import es.upsa.mimo.v2021.fitup.R
import es.upsa.mimo.v2021.fitup.databinding.FragmentExerciseListBinding
import es.upsa.mimo.v2021.fitup.utils.extensions.observe
import es.upsa.mimo.v2021.fitup.fragments.exercises.ExercisesFragment
import es.upsa.mimo.v2021.fitup.model.APIEntities.ExerciseDataSet
import es.upsa.mimo.v2021.fitup.persistence.PreferencesManager
import es.upsa.mimo.v2021.fitup.ui.exercises.ExerciseAdapter
import es.upsa.mimo.v2021.fitup.ui.trainingLists.exercises.TrainingListsExercisesActivity
import es.upsa.mimo.v2021.fitup.ui.trainingLists.exercises.TrainingListsExercisesViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TrainingListExerciseListFragment: ExercisesFragment() {
    private val viewModel: TrainingListsExercisesViewModel by sharedViewModel()
    val exerciseAdapter by lazy {
        ExerciseAdapter {
            viewModel.onItemClicked(
                it
            )
        }
    }

    companion object {
        fun newInstance(idTrainingListItem: Int? = null): TrainingListExerciseListFragment {
            val exerciseListFragment =
                TrainingListExerciseListFragment()
            if (idTrainingListItem != null) {
                val args = Bundle()
                args.putInt(
                    TrainingListsExercisesActivity.EXTRA_TRAINING_LIST_ITEM,
                    idTrainingListItem
                )
                exerciseListFragment.setArguments(args)
            }
            return exerciseListFragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentExerciseListBinding.bind(view)

        with(viewModel) {
            observe(items) {
                setLoading(false)
                exerciseAdapter.items = it
                val exerciseDataSet: ExerciseDataSet? = it.first()
                if (mDualPane && exerciseDataSet != null) {
                    showDetail(exerciseDataSet)
                }
            }
            observe(navigateToDetail) { event ->
                event.getContentIfNotHandled()?.let { showDetail(it) }
            }
            observe(trainingListItem) {
                binding.headerText.text = "${trainingListItem.value?.name} exercises:"
            }
        }
        getView()?.let { setupRecyclerView(it, exerciseAdapter, R.id.recyclerExerciseList) }

        if (savedInstanceState != null || viewModel.items.value != null) {
            return
        }

        setLoading(true)
        val trainingListItem: Int? = getArguments()?.getInt(TrainingListsExercisesActivity.EXTRA_TRAINING_LIST_ITEM)
        val email = activity?.applicationContext?.let { PreferencesManager(it).email }
        val userToken = activity?.applicationContext?.let { PreferencesManager(it).userToken }
        viewModel.onLoad(trainingListItem, email, userToken)

    }
}