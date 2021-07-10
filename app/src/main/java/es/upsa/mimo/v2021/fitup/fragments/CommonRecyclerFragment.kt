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
    protected var mProgressBar: ProgressBar? = null
    protected var mRecyclerView: RecyclerView? = null
    protected fun setupRecyclerView(view: View, exerciseAdapter: RecyclerView.Adapter<*>, recyclerId: Int) {
        mRecyclerView = view.findViewById(recyclerId)
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
}