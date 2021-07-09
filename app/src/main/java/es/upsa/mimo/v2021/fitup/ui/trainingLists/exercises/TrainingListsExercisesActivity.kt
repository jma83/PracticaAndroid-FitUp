package es.upsa.mimo.v2021.fitup.ui.trainingLists.exercises

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import es.upsa.mimo.v2021.fitup.R
import es.upsa.mimo.v2021.fitup.databinding.FragmentTrainingListsContainerBinding
import es.upsa.mimo.v2021.fitup.fragments.trainingLists.TrainingListExercisesFragment

class TrainingListsExercisesActivity: AppCompatActivity() {
    companion object {
        const val EXTRA_TRAINING_LIST_ITEM = "TrainingListsExercisesActivity:extraTrainingListItem"
    }
    private lateinit var binding: FragmentTrainingListsContainerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentTrainingListsContainerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            val trainingListItemId = intent.extras?.getInt(EXTRA_TRAINING_LIST_ITEM)
            val trainingListExercisesFragment: TrainingListExercisesFragment = TrainingListExercisesFragment.newInstance(trainingListItemId)
            supportFragmentManager.beginTransaction()
                .add(R.id.flContentTrainingLists, trainingListExercisesFragment).commit()
        }
    }
}