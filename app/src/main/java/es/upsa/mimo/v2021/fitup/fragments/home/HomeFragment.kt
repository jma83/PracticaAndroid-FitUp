package es.upsa.mimo.v2021.fitup.fragments.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import es.upsa.mimo.v2021.fitup.R
import es.upsa.mimo.v2021.fitup.ui.home.HomeViewModel
import es.upsa.mimo.v2021.fitup.utils.Constants

class HomeFragment: Fragment() {

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_exercises, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentFragmentManager.beginTransaction()
            .replace(R.id.flExerciseList, HomeExercisesFragment.newInstance())
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }

}