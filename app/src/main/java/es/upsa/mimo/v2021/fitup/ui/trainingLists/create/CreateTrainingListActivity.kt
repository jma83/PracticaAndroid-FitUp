package es.upsa.mimo.v2021.fitup.ui.trainingLists.create

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import es.upsa.mimo.v2021.fitup.databinding.ActivityCreateTrainingListBinding
import es.upsa.mimo.v2021.fitup.utils.extensions.observe
import es.upsa.mimo.v2021.fitup.persistence.PreferencesManager
import es.upsa.mimo.v2021.fitup.utils.extensions.showAlert
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateTrainingListActivity: AppCompatActivity() {
    private lateinit var binding: ActivityCreateTrainingListBinding
    private val viewModel: CreateTrainingListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateTrainingListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(viewModel) {
            observe(navigateToLists) { event ->
                event.getContentIfNotHandled()?.let { showTrainingLists() }
            }
            observe(showMessage) { event ->
                event.getContentIfNotHandled()?.let { showAlert(it.title, it.message) }
            }
        }

        binding.createListButton.setOnClickListener {
            val newNameList: String = binding.newNameList.text.toString()
            viewModel.onSubmit(newNameList)
        }
        val userEmail = PreferencesManager(applicationContext).email?: ""
        val userToken = PreferencesManager(applicationContext).userToken?: ""
        viewModel.onLoad(userEmail, userToken)
    }

    private fun showTrainingLists() {
        finish()
    }
}