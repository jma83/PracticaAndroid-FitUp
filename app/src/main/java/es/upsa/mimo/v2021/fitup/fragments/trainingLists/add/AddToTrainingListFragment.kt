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
import es.upsa.mimo.v2021.fitup.databinding.FragmentAddToTrainingListBinding
import es.upsa.mimo.v2021.fitup.utils.extensions.observe
import es.upsa.mimo.v2021.fitup.model.DBEntities.ExerciseItem
import es.upsa.mimo.v2021.fitup.persistence.PreferencesManager
import es.upsa.mimo.v2021.fitup.ui.trainingLists.add.AddToTrainingListViewModel
import es.upsa.mimo.v2021.fitup.ui.trainingLists.add.AddToTrainingListsAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

val TAG_EXERCISE_ITEM: String = "exerciseItem"

class AddToTrainingListFragment: DialogFragment() {
    private val viewModel: AddToTrainingListViewModel by sharedViewModel()
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
        val binding = FragmentAddToTrainingListBinding.bind(view)
        mProgressBar = binding.progressBar
        with(viewModel) {
            observe(items) {
                setLoading(false)
                addToTrainingListsAdapter.items = it
                if (it.size == 0) {
                    binding.emptyList.visibility = View.VISIBLE
                }
            }
        }
        setupRecyclerView(binding.recyclerTrainingList)

        if (viewModel.items.value != null && viewModel.items.value?.size!! > 0) {
            return
        }
        if (savedInstanceState != null) {
            return
        }

        setLoading(true)
        val exerciseItem = getArguments()?.getParcelable(TAG_EXERCISE_ITEM) as ExerciseItem?
        val email = activity?.applicationContext?.let { PreferencesManager(it).email }
        val userToken = activity?.applicationContext?.let { PreferencesManager(it).userToken }
        viewModel.onLoad(email, userToken, exerciseItem!!)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        mRecyclerView = recyclerView
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