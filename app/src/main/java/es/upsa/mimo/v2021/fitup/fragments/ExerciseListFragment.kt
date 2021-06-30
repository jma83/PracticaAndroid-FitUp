package es.upsa.mimo.v2021.fitup.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import es.upsa.mimo.v2021.fitup.R
import es.upsa.mimo.v2021.fitup.extensions.observe
import es.upsa.mimo.v2021.fitup.extensions.startActivity1
import es.upsa.mimo.v2021.fitup.model.APIEntities.Category
import es.upsa.mimo.v2021.fitup.model.APIEntities.ExerciseDataSet
import es.upsa.mimo.v2021.fitup.ui.detail.DetailActivity
import es.upsa.mimo.v2021.fitup.ui.exercises.ExercisesActivity
import es.upsa.mimo.v2021.fitup.ui.home.ExerciseAdapter
import es.upsa.mimo.v2021.fitup.ui.home.ExercisesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ExerciseListFragment : Fragment() {
    var mDualPane = false
    private var mProgressBar: ProgressBar? = null
    private var mRecyclerView: RecyclerView? = null
    private val viewModel: ExercisesViewModel by viewModel()
    val exerciseAdapter by lazy {
        ExerciseAdapter {
            viewModel.onItemClicked(
                it
            )
        }
    }

    companion object {
        fun newInstance(category: Category? = null): ExerciseListFragment {
            val exerciseListFragment = ExerciseListFragment()
            val args = Bundle()
            args.putSerializable(ExercisesActivity.EXTRA_CATEGORY, category)
            exerciseListFragment.setArguments(args)
            return exerciseListFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_exercise_list, container, false)
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
        getView()?.let { setupRecyclerView(it) }
        if (viewModel.items.value != null && viewModel.items.value?.size!! > 0) {
             return
        }
        if (savedInstanceState != null) {
            return
        }

        setLoading(true)
        val category: Category? = getArguments()?.getSerializable(ExercisesActivity.EXTRA_CATEGORY) as Category?
        viewModel.onLoad(category)

        setLoading(false)
    }

    private fun setupRecyclerView(view: View) {
        mRecyclerView = view.findViewById(R.id.recyclerExerciseList)
        if (mRecyclerView != null ) {
            mRecyclerView!!.adapter = exerciseAdapter
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

    private fun showDetail(exerciseDataSet: ExerciseDataSet) {
        if (mDualPane) {
            parentFragmentManager.beginTransaction()
                .replace(R.id.flExerciseDetail, ExerciseDetailFragment.newInstance(exerciseDataSet))
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit()
        } else {
            navigateToDetail(exerciseDataSet)
        }
    }


    private fun navigateToDetail(exerciseDataSet: ExerciseDataSet) {
        activity?.startActivity1<DetailActivity>(DetailActivity.EXTRA_ID to exerciseDataSet)
    }
}