package es.upsa.mimo.v2021.fitup.fragments.exercises

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
import es.upsa.mimo.v2021.fitup.extensions.startActivity1
import es.upsa.mimo.v2021.fitup.model.APIEntities.Category
import es.upsa.mimo.v2021.fitup.model.APIEntities.ExerciseDataSet
import es.upsa.mimo.v2021.fitup.ui.activities.detail.DetailActivity
import es.upsa.mimo.v2021.fitup.ui.exercises.CategoryExercisesActivity
import es.upsa.mimo.v2021.fitup.ui.exercises.ExerciseAdapter

open class ExercisesFragment: Fragment() {
    protected var mDualPane = false
    protected var mProgressBar: ProgressBar? = null
    protected var mRecyclerView: RecyclerView? = null
    protected fun setupRecyclerView(view: View, exerciseAdapter: ExerciseAdapter) {
        mRecyclerView = view.findViewById(R.id.recyclerExerciseList)
        if (mRecyclerView != null ) {
            mRecyclerView!!.adapter = exerciseAdapter
            mRecyclerView!!.setItemAnimator(DefaultItemAnimator())
        }
    }

    protected fun setLoading(loading: Boolean) {
        if (loading) {
            mProgressBar!!.visibility = View.VISIBLE
        } else {
            mProgressBar!!.visibility = View.GONE
        }
    }

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