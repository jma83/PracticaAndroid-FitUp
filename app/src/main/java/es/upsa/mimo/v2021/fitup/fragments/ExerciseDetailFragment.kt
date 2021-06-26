package es.upsa.mimo.v2021.fitup.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import es.upsa.mimo.v2021.fitup.R
import es.upsa.mimo.v2021.fitup.databinding.FragmentExerciseDetailBinding
import es.upsa.mimo.v2021.fitup.ui.detail.DetailActivity
import es.upsa.mimo.v2021.fitup.ui.detail.DetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class ExerciseDetailFragment: Fragment() {
    private val viewModel: DetailViewModel by viewModel()
    companion object {
        fun newInstance(id: Int): ExerciseDetailFragment {
            val exerciseDetailFragment = ExerciseDetailFragment()
            val args = Bundle()
            args.putInt(DetailActivity.EXTRA_ID, id)
            exerciseDetailFragment.setArguments(args)

            return exerciseDetailFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_exercise_detail, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = getArguments()?.getInt(DetailActivity.EXTRA_ID)?: 0
        viewModel.onCreate(id)
    }

}