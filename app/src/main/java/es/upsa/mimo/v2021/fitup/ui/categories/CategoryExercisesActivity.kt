package es.upsa.mimo.v2021.fitup.ui.categories

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import es.upsa.mimo.v2021.fitup.R
import es.upsa.mimo.v2021.fitup.databinding.FragmentCategoriesContainerBinding
import es.upsa.mimo.v2021.fitup.fragments.categories.CategoryExerciseListFragment
import es.upsa.mimo.v2021.fitup.fragments.categories.CategoryExercisesFragment
import es.upsa.mimo.v2021.fitup.fragments.exercises.ExercisesFragment
import es.upsa.mimo.v2021.fitup.model.APIEntities.Category

class CategoryExercisesActivity: AppCompatActivity() {
    companion object {
        const val EXTRA_CATEGORY = "CategoryExercisesActivity:extraCategory"
    }

    private lateinit var binding: FragmentCategoriesContainerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentCategoriesContainerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            val category = intent.extras?.getSerializable(EXTRA_CATEGORY) as Category?
            val categoryExercisesFragment: CategoryExercisesFragment = CategoryExercisesFragment.newInstance(category)
            supportFragmentManager.beginTransaction()
                .add(R.id.flContentCategories, categoryExercisesFragment).commit()
        }
    }
}