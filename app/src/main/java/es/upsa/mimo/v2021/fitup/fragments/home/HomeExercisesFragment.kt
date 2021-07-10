package es.upsa.mimo.v2021.fitup.fragments.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.upsa.mimo.v2021.fitup.R
import es.upsa.mimo.v2021.fitup.utils.extensions.observe
import es.upsa.mimo.v2021.fitup.fragments.exercises.ExercisesFragment
import es.upsa.mimo.v2021.fitup.model.APIEntities.ExerciseDataSet
import es.upsa.mimo.v2021.fitup.ui.exercises.ExerciseAdapter
import es.upsa.mimo.v2021.fitup.ui.home.HomeViewModel
import es.upsa.mimo.v2021.fitup.utils.Constants
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeExercisesFragment: ExercisesFragment() {
    private val viewModel: HomeViewModel by sharedViewModel()
    val exerciseAdapter by lazy {
        ExerciseAdapter {
            viewModel.onItemClicked(
                it
            )
        }
    }

    companion object {
        fun newInstance(): HomeExercisesFragment {
            return HomeExercisesFragment()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(Constants.APP_TAG, "onDestroy")
        Log.e(Constants.APP_TAG, viewModel.items.value.toString())
    }

    override fun onPause() {

        super.onPause()
        Log.e(Constants.APP_TAG, "onPause")
        Log.e("${Constants.APP_TAG}", viewModel.items.value.toString())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e(Constants.APP_TAG, "onCreateView")
        Log.e("${Constants.APP_TAG}", viewModel.items.value.toString())
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mProgressBar = view.findViewById(R.id.progressBar)
        val detailsFrame: View? = activity?.findViewById(R.id.flExerciseDetail)
        mDualPane = detailsFrame != null && detailsFrame.visibility == View.VISIBLE
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
        }
        getView()?.let { setupRecyclerView(it, exerciseAdapter, R.id.recyclerExerciseList) }
        if (viewModel.items.value != null && viewModel.items.value?.size!! > 0) {
            return
        }
        if (savedInstanceState != null) {
            return
        }

        setLoading(true)
        viewModel.onLoad()
    }
}