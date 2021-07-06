package es.upsa.mimo.v2021.fitup.fragments.trainingLists.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import es.upsa.mimo.v2021.fitup.R
import es.upsa.mimo.v2021.fitup.extensions.observe
import es.upsa.mimo.v2021.fitup.model.DBEntities.ExerciseItem
import es.upsa.mimo.v2021.fitup.persistence.PreferencesManager
import es.upsa.mimo.v2021.fitup.ui.trainingLists.add.AddToTrainingListViewModel
import es.upsa.mimo.v2021.fitup.ui.trainingLists.add.AddToTrainingListsAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

val TAG_EXERCISE_ITEM: String = "exerciseItem"

class AddToTrainingListFragment: DialogFragment() {
    private val viewModel: AddToTrainingListViewModel by viewModel()
    private var mProgressBar: ProgressBar? = null
    private var mRecyclerView: RecyclerView? = null
    val addToTrainingListsAdapter by lazy {
        AddToTrainingListsAdapter { it, isChecked ->
            viewModel.onAddToListClicked(
                it, isChecked
            )
        }
    }
    companion object {
        fun newInstance(exerciseItem: ExerciseItem): AddToTrainingListFragment {
            val addToTrainingListFragment =
                AddToTrainingListFragment()
            val args = Bundle()
            args.putParcelable(TAG_EXERCISE_ITEM, exerciseItem)
            addToTrainingListFragment.setArguments(args)

            return addToTrainingListFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_add_to_training_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mProgressBar = view.findViewById(R.id.progressBar)
        with(viewModel) {
            setLoading(false)
            observe(items) {
                addToTrainingListsAdapter.items = it
            }
        }
        view.let { setupRecyclerView(it) }

        if (viewModel.items.value != null && viewModel.items.value?.size!! > 0) {
            return
        }
        if (savedInstanceState != null) {
            return
        }

        setLoading(true)
        val exerciseItem = getArguments()?.getParcelable(TAG_EXERCISE_ITEM) as ExerciseItem?
        viewModel.onLoad(context?.let { PreferencesManager(it).email }, exerciseItem!!)
    }

    private fun setupRecyclerView(view: View) {
        mRecyclerView = view.findViewById(R.id.recyclerTrainingList)
        if (mRecyclerView != null ) {
            mRecyclerView!!.adapter = addToTrainingListsAdapter
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

}