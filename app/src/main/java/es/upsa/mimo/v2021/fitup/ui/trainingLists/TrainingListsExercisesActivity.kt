package es.upsa.mimo.v2021.fitup.ui.trainingLists

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import es.upsa.mimo.v2021.fitup.R
import es.upsa.mimo.v2021.fitup.databinding.ActivityTrainingListsBinding
import es.upsa.mimo.v2021.fitup.fragments.exercises.ExercisesFragment
import es.upsa.mimo.v2021.fitup.fragments.trainingLists.TrainingListExerciseListFragment
import es.upsa.mimo.v2021.fitup.model.APIEntities.Exercises
import es.upsa.mimo.v2021.fitup.model.DBEntities.TrainingListItem

class TrainingListsExercisesActivity: AppCompatActivity() {
    companion object {
        const val EXTRA_TRAINING_LIST_ITEM = "TrainingListsExercisesActivity:extraTrainingListItem"
    }

    private lateinit var binding: ActivityTrainingListsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTrainingListsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            val trainingListItem = intent.extras?.getParcelable(EXTRA_TRAINING_LIST_ITEM) as TrainingListItem?
            val exercisesFragment: ExercisesFragment = TrainingListExerciseListFragment.newInstance(trainingListItem)
            supportFragmentManager.beginTransaction()
                .add(R.id.flContentTrainingLists, exercisesFragment).commit()
        }
    }
}