package es.upsa.mimo.v2021.fitup.fragments.trainingLists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import es.upsa.mimo.v2021.fitup.R
import es.upsa.mimo.v2021.fitup.fragments.categories.CategoryExerciseListFragment
import es.upsa.mimo.v2021.fitup.fragments.categories.CategoryExercisesFragment
import es.upsa.mimo.v2021.fitup.model.APIEntities.Category
import es.upsa.mimo.v2021.fitup.model.DBEntities.TrainingListItem
import es.upsa.mimo.v2021.fitup.ui.categories.CategoryExercisesActivity
import es.upsa.mimo.v2021.fitup.ui.trainingLists.exercises.TrainingListsExercisesActivity

class TrainingListExercisesFragment: Fragment() {
    companion object {
        fun newInstance(trainingListItem: TrainingListItem? = null): TrainingListExercisesFragment {
            val exercisesFragment =
                TrainingListExercisesFragment()
            if (trainingListItem != null) {
                val args = Bundle()
                args.putParcelable(TrainingListsExercisesActivity.EXTRA_TRAINING_LIST_ITEM, trainingListItem)
                exercisesFragment.setArguments(args)
            }
            return exercisesFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_exercises, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val trainingListItem: TrainingListItem? = getArguments()?.getParcelable<TrainingListItem?>(TrainingListsExercisesActivity.EXTRA_TRAINING_LIST_ITEM)
        parentFragmentManager.beginTransaction()
            .replace(R.id.flExerciseList, TrainingListExerciseListFragment.newInstance(trainingListItem))
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }
}