package es.upsa.mimo.v2021.fitup.fragments.trainingLists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import es.upsa.mimo.v2021.fitup.R
import es.upsa.mimo.v2021.fitup.utils.extensions.observe
import es.upsa.mimo.v2021.fitup.utils.extensions.startActivity1
import es.upsa.mimo.v2021.fitup.model.DBEntities.TrainingListItem
import es.upsa.mimo.v2021.fitup.persistence.PreferencesManager
import es.upsa.mimo.v2021.fitup.ui.categories.CategoryExercisesActivity
import es.upsa.mimo.v2021.fitup.ui.trainingLists.TrainingListsAdapter
import es.upsa.mimo.v2021.fitup.ui.trainingLists.exercises.TrainingListsExercisesActivity
import es.upsa.mimo.v2021.fitup.ui.trainingLists.TrainingListsViewModel
import es.upsa.mimo.v2021.fitup.ui.trainingLists.create.CreateTrainingListActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class TrainingListsListFragment: Fragment() {

    private var mProgressBar: ProgressBar? = null
    private var mRecyclerView: RecyclerView? = null
    private var addListButton: FloatingActionButton? = null
    private val viewModel: TrainingListsViewModel by viewModel()
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
        mProgressBar = view.findViewById(R.id.progressBar)
        with(viewModel) {
            observe(items) {
                trainingListsAdapter.items = it
            }
            observe(navigateToExerciseList) { event ->
                event.getContentIfNotHandled()?.let { showExerciseList(it) }
            }
            observe(navigateToCreateList) { event ->
                event.getContentIfNotHandled()?.let { showCreateList() }
            }
        }
        view.let { setupRecyclerView(it) }

        addListButton = view.findViewById(R.id.addTrainingList) as FloatingActionButton
        addListButton?.setOnClickListener { viewModel.onCreateClicked() }

        if (viewModel.items.value != null && viewModel.items.value?.size!! > 0) {
            return
        }
        if (savedInstanceState != null) {
            return
        }

        setLoading(true)
        viewModel.onLoad(context?.let { PreferencesManager(it).email })
        setLoading(false)
    }

    private fun setupRecyclerView(view: View) {
        mRecyclerView = view.findViewById(R.id.recyclerTrainingList)
        if (mRecyclerView != null ) {
            mRecyclerView!!.adapter = trainingListsAdapter
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

    private fun showExerciseList(trainingListItem: TrainingListItem) {
        activity?.startActivity1<TrainingListsExercisesActivity>(
            TrainingListsExercisesActivity.EXTRA_TRAINING_LIST_ITEM to trainingListItem)
    }

    private fun showCreateList() {
        activity?.startActivity1<CreateTrainingListActivity>()
    }
}