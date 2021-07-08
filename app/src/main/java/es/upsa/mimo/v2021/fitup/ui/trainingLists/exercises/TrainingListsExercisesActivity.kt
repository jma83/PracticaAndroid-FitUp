package es.upsa.mimo.v2021.fitup.ui.trainingLists.exercises

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import es.upsa.mimo.v2021.fitup.R
import es.upsa.mimo.v2021.fitup.databinding.ActivityCategoriesBinding
import es.upsa.mimo.v2021.fitup.databinding.ActivityTrainingListsBinding
import es.upsa.mimo.v2021.fitup.fragments.trainingLists.TrainingListExercisesFragment
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
            val trainingListItemId = intent.extras?.getInt(EXTRA_TRAINING_LIST_ITEM)
            val trainingListExercisesFragment: TrainingListExercisesFragment = TrainingListExercisesFragment.newInstance(trainingListItemId)
            supportFragmentManager.beginTransaction()
                .add(R.id.flContentTrainingLists, trainingListExercisesFragment).commit()
        }
    }
}