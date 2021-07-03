package es.upsa.mimo.v2021.fitup.fragments.exercises

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import es.upsa.mimo.v2021.fitup.R
import es.upsa.mimo.v2021.fitup.model.APIEntities.Category
import es.upsa.mimo.v2021.fitup.ui.exercises.CategoryExercisesActivity

class CategoryExercisesFragment: Fragment() {

    companion object {
        fun newInstance(category: Category? = null): CategoryExercisesFragment {
            val exercisesFragment =
                CategoryExercisesFragment()
            if (category != null) {
                val args = Bundle()
                args.putSerializable(CategoryExercisesActivity.EXTRA_CATEGORY, category)
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
        val category: Category? = getArguments()?.getSerializable(CategoryExercisesActivity.EXTRA_CATEGORY) as Category?
        parentFragmentManager.beginTransaction()
            .replace(R.id.flExerciseList, ExerciseListFragment.newInstance(category))
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }

}