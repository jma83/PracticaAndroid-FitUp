package es.upsa.mimo.v2021.fitup.fragments.trainingLists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import es.upsa.mimo.v2021.fitup.R
import es.upsa.mimo.v2021.fitup.fragments.categories.CategoryListFragment

class TrainingListsFragment: Fragment() {
    companion object {
        fun newInstance(): TrainingListsFragment {
            return TrainingListsFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_training_lists, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentFragmentManager.beginTransaction()
            .replace(
                R.id.flContentTrainingLists,
                CategoryListFragment.newInstance()
            )
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }
}