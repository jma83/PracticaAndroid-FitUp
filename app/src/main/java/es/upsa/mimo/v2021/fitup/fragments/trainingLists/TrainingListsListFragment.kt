package es.upsa.mimo.v2021.fitup.fragments.trainingLists

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import es.upsa.mimo.v2021.fitup.R
import es.upsa.mimo.v2021.fitup.extensions.observe
import es.upsa.mimo.v2021.fitup.extensions.startActivity1
import es.upsa.mimo.v2021.fitup.model.DBEntities.TrainingListItem
import es.upsa.mimo.v2021.fitup.persistence.PreferencesManager
import es.upsa.mimo.v2021.fitup.ui.categories.CategoryExercisesActivity
import es.upsa.mimo.v2021.fitup.ui.trainingLists.TrainingListsAdapter
import es.upsa.mimo.v2021.fitup.ui.trainingLists.exercises.TrainingListsExercisesActivity
import es.upsa.mimo.v2021.fitup.ui.trainingLists.TrainingListsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class TrainingListsListFragment: Fragment() {

    private var mProgressBar: ProgressBar? = null
    private var mRecyclerView: RecyclerView? = null
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
        }
        getView()?.let { setupRecyclerView(it) }
        if (viewModel.items.value != null && viewModel.items.value?.size!! > 0) {
            return
        }
        if (savedInstanceState != null) {
            return
        }

        setLoading(true)

        viewModel.onCreate(context?.let { PreferencesManager(it).email })
        setLoading(false)
    }

    private fun setupRecyclerView(view: View) {
        mRecyclerView = view.findViewById(R.id.recyclerCategoryList)
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
        activity?.startActivity1<CategoryExercisesActivity>(
            TrainingListsExercisesActivity.EXTRA_TRAINING_LIST_ITEM to trainingListItem)
    }
}