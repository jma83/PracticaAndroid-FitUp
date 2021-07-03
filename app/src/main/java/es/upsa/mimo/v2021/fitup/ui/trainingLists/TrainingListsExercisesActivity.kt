package es.upsa.mimo.v2021.fitup.ui.trainingLists

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import es.upsa.mimo.v2021.fitup.R
import es.upsa.mimo.v2021.fitup.databinding.ActivityCategoriesBinding
import es.upsa.mimo.v2021.fitup.databinding.ActivityTrainingListsBinding
import es.upsa.mimo.v2021.fitup.fragments.exercises.ExercisesFragment
import es.upsa.mimo.v2021.fitup.model.APIEntities.Category
import es.upsa.mimo.v2021.fitup.model.APIEntities.Exercise
import es.upsa.mimo.v2021.fitup.model.APIEntities.Exercises
import es.upsa.mimo.v2021.fitup.ui.exercises.CategoryExercisesActivity

class TrainingListsExercisesActivity: AppCompatActivity() {
    companion object {
        const val EXTRA_EXERCISES = "TrainingListsExercisesActivity:extraExercises"
    }

    private lateinit var binding: ActivityTrainingListsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTrainingListsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            val exercises = intent.extras?.getSerializable(TrainingListsExercisesActivity.EXTRA_EXERCISES) as List<*>?
            val exercisesFragment: ExercisesFragment = ExercisesFragment.newInstance(exercises)
            supportFragmentManager.beginTransaction()
                .add(R.id.flContentCategories, exercisesFragment).commit()
        }
    }
}