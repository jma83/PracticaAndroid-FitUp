package es.upsa.mimo.v2021.fitup.fragments.exercises

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import es.upsa.mimo.v2021.fitup.R
import es.upsa.mimo.v2021.fitup.databinding.FragmentExerciseListBinding
import es.upsa.mimo.v2021.fitup.databinding.FragmentExercisesBinding
import es.upsa.mimo.v2021.fitup.utils.extensions.startNewActivity
import es.upsa.mimo.v2021.fitup.fragments.CommonRecyclerFragment
import es.upsa.mimo.v2021.fitup.model.APIEntities.ExerciseDataSet
import es.upsa.mimo.v2021.fitup.ui.detail.DetailActivity

open class ExercisesFragment: CommonRecyclerFragment() {
    protected var mDualPane = false

    protected fun showDetail(exerciseDataSet: ExerciseDataSet) {
        if (mDualPane) {
            parentFragmentManager.beginTransaction()
                .replace(R.id.flExerciseDetail, ExerciseDetailFragment.newInstance(exerciseDataSet))
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit()
        } else {
            navigateToDetail(exerciseDataSet)
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
        val binding = FragmentExerciseListBinding.bind(view)
        if (mRecyclerView?.adapter?.itemCount == 0) {
            binding.emptyList.visibility = View.VISIBLE
        }
        mProgressBar = binding.progressBar
        val detailsFrame: View? = activity?.findViewById(R.id.flExerciseDetail)
        mDualPane = detailsFrame != null && detailsFrame.visibility == View.VISIBLE
    }

    protected fun navigateToDetail(exerciseDataSet: ExerciseDataSet) {
        activity?.startNewActivity<DetailActivity>(DetailActivity.EXTRA_ID to exerciseDataSet)
    }

}