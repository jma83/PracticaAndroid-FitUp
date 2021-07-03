package es.upsa.mimo.v2021.fitup.ui.exercises

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import es.upsa.mimo.v2021.fitup.R
import es.upsa.mimo.v2021.fitup.databinding.ActivityCategoriesBinding
import es.upsa.mimo.v2021.fitup.fragments.categories.CategoryExerciseListFragment
import es.upsa.mimo.v2021.fitup.fragments.exercises.ExercisesFragment
import es.upsa.mimo.v2021.fitup.model.APIEntities.Category

class CategoryExercisesActivity: AppCompatActivity() {
    companion object {
        const val EXTRA_CATEGORY = "CategoryExercisesActivity:extraCategory"
    }

    private lateinit var binding: ActivityCategoriesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCategoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            val category = intent.extras?.getSerializable(EXTRA_CATEGORY) as Category?
            val exercisesFragment: ExercisesFragment = CategoryExerciseListFragment.newInstance(category)
            supportFragmentManager.beginTransaction()
                .add(R.id.flContentCategories, exercisesFragment).commit()
        }
    }
}