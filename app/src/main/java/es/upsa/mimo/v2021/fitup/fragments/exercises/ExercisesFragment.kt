package es.upsa.mimo.v2021.fitup.fragments.exercises

import androidx.fragment.app.FragmentTransaction
import es.upsa.mimo.v2021.fitup.R
import es.upsa.mimo.v2021.fitup.utils.extensions.startActivity1
import es.upsa.mimo.v2021.fitup.fragments.CommonRecyclerFragment
import es.upsa.mimo.v2021.fitup.model.APIEntities.ExerciseDataSet
import es.upsa.mimo.v2021.fitup.ui.detail.DetailActivity

open class ExercisesFragment: CommonRecyclerFragment() {

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

    protected fun navigateToDetail(exerciseDataSet: ExerciseDataSet) {
        activity?.startActivity1<DetailActivity>(DetailActivity.EXTRA_ID to exerciseDataSet)
    }

}