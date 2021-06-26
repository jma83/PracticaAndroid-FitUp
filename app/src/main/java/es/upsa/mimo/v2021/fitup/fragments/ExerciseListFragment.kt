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
import es.upsa.mimo.v2021.fitup.model.APIEntities.Exercise
import es.upsa.mimo.v2021.fitup.model.APIEntities.ExerciseDataSet
import es.upsa.mimo.v2021.fitup.ui.detail.DetailActivity
import es.upsa.mimo.v2021.fitup.ui.home.ExerciseAdapter
import es.upsa.mimo.v2021.fitup.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ExerciseListFragment : Fragment() {
    private var mProgressBar: ProgressBar? = null
    var mDualPane = false
    private var mRecyclerView: RecyclerView? = null
    private val viewModel: HomeViewModel by viewModel()
    val exerciseAdapter by lazy {
        ExerciseAdapter {
            viewModel.onItemClicked(
                it
            )
        }
    }

    companion object {
        fun newInstance(): ExerciseListFragment {
            return ExerciseListFragment()
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
        if (savedInstanceState != null) {
            return
        }
        with(viewModel) {
            observe(items) {
                exerciseAdapter.items = it
            }
            observe(navigateToDetail) { event ->
                event.getContentIfNotHandled()?.let { showDetail(it) }
            }
        }
        getView()?.let { setupRecyclerView(it) }
        setLoading(true)
        viewModel.onLoad()
        val exerciseDataSet: ExerciseDataSet? = viewModel.items.value?.first()
        if (mDualPane && exerciseDataSet != null) {
            showDetail(exerciseDataSet)
        }
        setLoading(false)
    }

    private fun setupRecyclerView(view: View) {
        mRecyclerView = view.findViewById(R.id.recyclerExerciseList)
        if (mRecyclerView != null ) {
            /*mRecyclerView!!.setLayoutManager(
                LinearLayoutManager(
                    getActivity(),
                    LinearLayoutManager.VERTICAL,
                    false
                )
            )*/
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
            val exerciseDetailFragment: ExerciseDetailFragment = ExerciseDetailFragment.newInstance(exerciseDataSet)
            parentFragmentManager.beginTransaction()
                .replace(R.id.flExerciseDetail, exerciseDetailFragment)
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