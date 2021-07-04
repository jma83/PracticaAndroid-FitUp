package es.upsa.mimo.v2021.fitup.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import es.upsa.mimo.v2021.fitup.R
import es.upsa.mimo.v2021.fitup.ui.exercises.ExerciseAdapter

open class CommonRecyclerFragment: Fragment() {
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_exercise_list, container, false)
    }

    protected fun setLoading(loading: Boolean) {
        if (loading) {
            mProgressBar!!.visibility = View.VISIBLE
        } else {
            mProgressBar!!.visibility = View.GONE
        }
    }
}