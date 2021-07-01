package es.upsa.mimo.v2021.fitup.fragments.exercises

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import es.upsa.mimo.v2021.fitup.R
import es.upsa.mimo.v2021.fitup.databinding.FragmentExerciseDetailBinding
import es.upsa.mimo.v2021.fitup.extensions.fromUrl
import es.upsa.mimo.v2021.fitup.extensions.observe
import es.upsa.mimo.v2021.fitup.model.APIEntities.ExerciseDataSet
import es.upsa.mimo.v2021.fitup.ui.activities.detail.DetailActivity
import es.upsa.mimo.v2021.fitup.ui.activities.detail.DetailViewModel
import es.upsa.mimo.v2021.fitup.ui.activities.detail.MuscleAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel


class ExerciseDetailFragment: Fragment() {
    private val viewModel: DetailViewModel by viewModel()
    private lateinit var binding: FragmentExerciseDetailBinding
    val muscleAdapter by lazy{ MuscleAdapter() }
    private var mRecyclerView: RecyclerView? = null

    companion object {
        fun newInstance(exerciseDataSet: ExerciseDataSet): ExerciseDetailFragment {
            val exerciseDetailFragment =
                ExerciseDetailFragment()
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
                    Log.i("es.upsa.mimo.fitup", img);
                    detailThumb.fromUrl(img)
                    exerciseDescription.text = item.value?.exerciseInfo?.description?.let { it1 ->
                        HtmlCompat.fromHtml(
                            it1, 0)
                    } ?: "N/A"
                }
            }
            observe(muscles){
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