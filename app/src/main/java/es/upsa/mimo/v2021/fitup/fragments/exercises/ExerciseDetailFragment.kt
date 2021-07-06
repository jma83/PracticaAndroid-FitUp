package es.upsa.mimo.v2021.fitup.fragments.exercises

import android.annotation.SuppressLint
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
import es.upsa.mimo.v2021.fitup.fragments.trainingLists.add.AddToTrainingListFragment
import es.upsa.mimo.v2021.fitup.model.APIEntities.ExerciseDataSet
import es.upsa.mimo.v2021.fitup.model.DBEntities.ExerciseItem
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

    @SuppressLint("SetTextI18n")
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
                    val img: String? = item.value?.exerciseImage?.image
                    if (img != null) {
                        Log.i("es.upsa.mimo.fitup", img);
                        detailThumb.fromUrl(img)
                    }
                    if (item.value?.exerciseInfo?.muscles != null && item.value?.exerciseInfo?.muscles!!.size > 0) {
                        muscleTitle.text = getString(R.string.musclesList)
                    }
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
                    if (category.value!=null) {
                        categoryText.text = "Category ${category.value?.name}"
                    }
                }
            }
        }

        binding.addToTrainingList.setOnClickListener {
            with(viewModel.item.value!!) {
                val exerciseItem = ExerciseItem(exerciseInfo.id, exerciseInfo.name, this.exerciseInfo.exercise_base,
                    this.exerciseInfo.description, this.exerciseInfo.category, this.exerciseInfo.muscles,
                    this.exerciseInfo.language,this.exerciseImage?.image)

                val addToTrainingListFragment = AddToTrainingListFragment.newInstance(exerciseItem)
                addToTrainingListFragment.dialog?.setCanceledOnTouchOutside(true)
                activity?.supportFragmentManager?.let { it1 -> addToTrainingListFragment.show(it1, "addToTrainingList") }
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