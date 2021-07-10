package es.upsa.mimo.v2021.fitup.fragments.trainingLists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.floatingactionbutton.FloatingActionButton
import es.upsa.mimo.v2021.fitup.R
import es.upsa.mimo.v2021.fitup.databinding.FragmentTrainingListsBinding
import es.upsa.mimo.v2021.fitup.fragments.CommonRecyclerFragment
import es.upsa.mimo.v2021.fitup.fragments.alert.AlertFragment
import es.upsa.mimo.v2021.fitup.model.DBEntities.TrainingListItem
import es.upsa.mimo.v2021.fitup.persistence.PreferencesManager
import es.upsa.mimo.v2021.fitup.ui.trainingLists.ErrorData
import es.upsa.mimo.v2021.fitup.ui.trainingLists.TrainingListsAdapter
import es.upsa.mimo.v2021.fitup.ui.trainingLists.exercises.TrainingListsExercisesActivity
import es.upsa.mimo.v2021.fitup.ui.trainingLists.TrainingListsViewModel
import es.upsa.mimo.v2021.fitup.ui.trainingLists.create.CreateTrainingListActivity
import es.upsa.mimo.v2021.fitup.utils.extensions.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TrainingListsListFragment: CommonRecyclerFragment() {

    private var addListButton: FloatingActionButton? = null
    private val viewModel: TrainingListsViewModel by sharedViewModel()
    val trainingListsAdapter by lazy {
        TrainingListsAdapter {
            viewModel.onItemClicked(
                it
            )
        }
    }

    companion object {
        fun newInstance(): TrainingListsListFragment {
            return TrainingListsListFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_training_lists, container, false)
    }

    override fun onResume() {
        super.onResume()
        viewModel.onLoad(context?.let { PreferencesManager(it).email })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentTrainingListsBinding.bind(view)
        mProgressBar = binding.progressBar
        with(viewModel) {
            observe(items) {
                setLoading(false)
                trainingListsAdapter.items = it
            }
            observe(navigateToExerciseList) { event ->
                event.getContentIfNotHandled()?.let { showExerciseList(it) }
            }
            observe(navigateToCreateList) { event ->
                event.getContentIfNotHandled()?.let { showCreateList() }
            }
            observe(showError) {event ->
                event.getContentIfNotHandled()?.let { showAlertError(it) }
            }
        }
        view.let { setupRecyclerView(it, trainingListsAdapter, R.id.recyclerTrainingList) }

        addListButton = binding.addTrainingList
        addListButton?.setOnClickListener { viewModel.onCreateClicked() }

        if (viewModel.items.value != null && viewModel.items.value?.size!! > 0) {
            return
        }
        if (savedInstanceState != null) {
            return
        }

        setLoading(true)
        viewModel.onLoad(context?.let { PreferencesManager(it).email })
    }

    private fun showExerciseList(trainingListItem: TrainingListItem) {
        activity?.startNewActivity<TrainingListsExercisesActivity>(
            TrainingListsExercisesActivity.EXTRA_TRAINING_LIST_ITEM to trainingListItem.id)
    }

    private fun showCreateList() {
        activity?.startNewActivity<CreateTrainingListActivity>()
    }

    private fun showAlertError(errorData: ErrorData) {
        val alertFragment = AlertFragment.newInstance(errorData.title, errorData.message)
        alertFragment.dialog?.setCanceledOnTouchOutside(true)
        activity?.supportFragmentManager?.let { it1 -> alertFragment.show(it1, "alertError") }
    }
}