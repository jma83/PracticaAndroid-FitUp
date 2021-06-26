package es.upsa.mimo.v2021.fitup.ui.detail

import android.R
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import es.upsa.mimo.v2021.fitup.databinding.FragmentExerciseDetailBinding
import es.upsa.mimo.v2021.fitup.fragments.ExerciseDetailFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity: AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "DetailActivity:extraId"
    }

    private lateinit var binding: FragmentExerciseDetailBinding
    private val viewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish()
            return
        }

        binding = FragmentExerciseDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.onCreate(intent.getIntExtra(EXTRA_ID, 0))

        if (savedInstanceState == null) {
            val exerciseDetailFragment: ExerciseDetailFragment = ExerciseDetailFragment.newInstance(intent.extras?.getInt(EXTRA_ID) ?: 0)
            supportFragmentManager.beginTransaction()
                .add(R.id.content, exerciseDetailFragment).commit()
        }
    }
}