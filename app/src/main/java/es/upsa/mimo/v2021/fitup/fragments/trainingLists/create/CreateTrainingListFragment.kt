package es.upsa.mimo.v2021.fitup.fragments.trainingLists.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import es.upsa.mimo.v2021.fitup.R
import es.upsa.mimo.v2021.fitup.utils.extensions.observe
import es.upsa.mimo.v2021.fitup.utils.extensions.startActivity1
import es.upsa.mimo.v2021.fitup.persistence.PreferencesManager
import es.upsa.mimo.v2021.fitup.ui.MainActivity
import es.upsa.mimo.v2021.fitup.ui.trainingLists.create.CreateTrainingListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateTrainingListFragment: Fragment() {
    private val viewModel: CreateTrainingListViewModel by viewModel()
    private lateinit var userEmail: String

    companion object {
        fun newInstance(): CreateTrainingListFragment {
            return CreateTrainingListFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_create_training_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewModel) {
            observe(navigateToLists) { event ->
                event.getContentIfNotHandled()?.let { showTrainingLists() }
            }
        }
        userEmail = PreferencesManager(activity?.applicationContext!!).email?: ""

        val createListButton: Button = view.findViewById(R.id.createListButton)
        val newNameList: EditText = view.findViewById(R.id.newNameList)
        createListButton.setOnClickListener {
            val newNameList: String = newNameList.text.toString()
            viewModel.onSubmit(newNameList, userEmail)
        }
    }

    private fun showTrainingLists() {
        activity?.startActivity1<MainActivity>()
    }
}