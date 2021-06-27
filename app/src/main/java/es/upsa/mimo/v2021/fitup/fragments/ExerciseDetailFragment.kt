package es.upsa.mimo.v2021.fitup.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import es.upsa.mimo.v2021.fitup.R
import es.upsa.mimo.v2021.fitup.databinding.FragmentExerciseDetailBinding
import es.upsa.mimo.v2021.fitup.extensions.fromUrl
import es.upsa.mimo.v2021.fitup.extensions.observe
import es.upsa.mimo.v2021.fitup.model.APIEntities.ExerciseDataSet
import es.upsa.mimo.v2021.fitup.ui.detail.DetailActivity
import es.upsa.mimo.v2021.fitup.ui.detail.DetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class ExerciseDetailFragment: Fragment() {
    private val viewModel: DetailViewModel by viewModel()
    private lateinit var binding: FragmentExerciseDetailBinding
    companion object {
        fun newInstance(exerciseDataSet: ExerciseDataSet): ExerciseDetailFragment {
            val exerciseDetailFragment = ExerciseDetailFragment()
            val args = Bundle()
            args.putSerializable(DetailActivity.EXTRA_ID, exerciseDataSet)
            exerciseDetailFragment.setArguments(args)

            return exerciseDetailFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_exercise_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val exercise: ExerciseDataSet? = getArguments()?.getSerializable(DetailActivity.EXTRA_ID) as ExerciseDataSet?
        if (exercise != null) {
            viewModel.onCreate(exercise)
        }
        binding = FragmentExerciseDetailBinding.bind(view)

        with(viewModel) {
            observe(item){
                with(binding) {
                    this.exerciseTitle.text = viewModel.item.value?.exerciseInfo?.name ?: "N/A"
                    val img: String = viewModel.item.value?.exerciseImage?.image ?: "https://www.vippng.com/png/detail/221-2210873_aerobic-exercise-icon.png"
                    detailThumb.fromUrl(img)
                }
            }
        }
    }
}