package es.upsa.mimo.v2021.fitup.ui.detail

import android.R
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import es.upsa.mimo.v2021.fitup.databinding.FragmentExerciseDetailBinding
import es.upsa.mimo.v2021.fitup.fragments.ExerciseDetailFragment
import es.upsa.mimo.v2021.fitup.model.APIEntities.ExerciseDataSet
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity: AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "DetailActivity:extraId"
    }

    private lateinit var binding: FragmentExerciseDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish()
            return
        }

        binding = FragmentExerciseDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            val exercise = intent.extras?.getSerializable(EXTRA_ID) as ExerciseDataSet
            val exerciseDetailFragment: ExerciseDetailFragment = ExerciseDetailFragment.newInstance(exercise)
            supportFragmentManager.beginTransaction()
                .add(R.id.content, exerciseDetailFragment).commit()
        }
    }
}