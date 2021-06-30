package es.upsa.mimo.v2021.fitup.ui.exercises

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import es.upsa.mimo.v2021.fitup.R
import es.upsa.mimo.v2021.fitup.databinding.FragmentExerciseDetailBinding
import es.upsa.mimo.v2021.fitup.databinding.FragmentExerciseListBinding
import es.upsa.mimo.v2021.fitup.fragments.ExerciseListFragment
import es.upsa.mimo.v2021.fitup.model.APIEntities.Category

class ExercisesActivity: AppCompatActivity() {
    companion object {
        const val EXTRA_CATEGORY = "ExercisesActivity:extraCategory"
    }

    private lateinit var binding: FragmentExerciseListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentExerciseListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            val category = intent.extras?.getSerializable(EXTRA_CATEGORY) as Category?
            val exerciseListFragment: ExerciseListFragment = ExerciseListFragment.newInstance(category)
            supportFragmentManager.beginTransaction()
                .add(R.id.flContent, exerciseListFragment).commit()
        }
    }
}