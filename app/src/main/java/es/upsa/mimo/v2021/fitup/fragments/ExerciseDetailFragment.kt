package es.upsa.mimo.v2021.fitup.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import es.upsa.mimo.v2021.fitup.R
import es.upsa.mimo.v2021.fitup.databinding.FragmentExerciseDetailBinding
import es.upsa.mimo.v2021.fitup.extensions.fromUrl
import es.upsa.mimo.v2021.fitup.extensions.observe
import es.upsa.mimo.v2021.fitup.model.APIEntities.ExerciseDataSet
import es.upsa.mimo.v2021.fitup.ui.detail.DetailActivity
import es.upsa.mimo.v2021.fitup.ui.detail.DetailViewModel
import es.upsa.mimo.v2021.fitup.ui.detail.MuscleAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel


class ExerciseDetailFragment: Fragment() {
    private val viewModel: DetailViewModel by viewModel()
    private lateinit var binding: FragmentExerciseDetailBinding
    val muscleAdapter by lazy{ MuscleAdapter() }
    private var mRecyclerView: RecyclerView? = null

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
        setupRecyclerView(view)

        with(viewModel) {
            observe(item){
                with(binding) {
                    exerciseTitle.text = item.value?.exerciseInfo?.name ?: "N/A"
                    val img: String = item.value?.exerciseImage?.image ?: "https://www.vippng.com/png/detail/221-2210873_aerobic-exercise-icon.png"
                    detailThumb.fromUrl(img)
                    exerciseDescription.text = item.value?.exerciseInfo?.description ?: "N/A"
                }
            }
            observe(muscles){
                with(binding) {
                    muscleTitle.text = "Muscles"
                }
                muscleAdapter.items = muscles.value ?: emptyList()
            }

            observe(category) {
                with(binding) {
                    categoryText.text = "Category ${category.value?.name}"
                }
            }
        }
    }

    private fun setupRecyclerView(view: View) {
        mRecyclerView = view.findViewById(R.id.recyclerMuscleList)
        if (mRecyclerView != null ) {
            mRecyclerView!!.adapter = muscleAdapter
            mRecyclerView!!.setItemAnimator(DefaultItemAnimator())
        }
    }
}