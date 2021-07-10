package es.upsa.mimo.v2021.fitup.ui.register

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import es.upsa.mimo.v2021.fitup.databinding.ActivityRegisterBinding
import es.upsa.mimo.v2021.fitup.model.DBEntities.UserItem
import es.upsa.mimo.v2021.fitup.persistence.PreferencesManager
import es.upsa.mimo.v2021.fitup.utils.extensions.startNewActivity
import es.upsa.mimo.v2021.fitup.ui.MainActivity
import es.upsa.mimo.v2021.fitup.utils.extensions.observe
import es.upsa.mimo.v2021.fitup.utils.extensions.showAlert
import org.koin.androidx.viewmodel.ext.android.viewModel


private lateinit var binding: ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private val viewModel: RegisterViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setup()
    }

    fun setup() {
        with(viewModel) {
            observe(navigateToHome) { event ->
                event.getContentIfNotHandled()?.let {
                    setPreferences(it)
                    showHome()
                }
            }
            observe(showMessage){ event ->
                event.getContentIfNotHandled()?.let { showAlert(it.title, it.message) }
            }
        }

        binding.button1.setOnClickListener{
            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextPassword.text.toString()
            val name = binding.editTextName.text.toString()
            val age: String = binding.editTextAge.text.toString()
            viewModel.onSubmit(email, password, name, age.toIntOrNull())
        }
    }

    private fun showHome() {
        startNewActivity<MainActivity>(
            MainActivity.EXTRA_EMAIL to "email")
        finish()
    }

    private fun setPreferences(user: UserItem) {
        val preferencesManager = PreferencesManager(applicationContext)
        preferencesManager.email = user.email
        preferencesManager.userToken = user.userToken
    }

}