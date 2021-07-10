package es.upsa.mimo.v2021.fitup.ui.start

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import es.upsa.mimo.v2021.fitup.databinding.ActivityStartBinding
import es.upsa.mimo.v2021.fitup.utils.extensions.observe
import es.upsa.mimo.v2021.fitup.persistence.db.FitUpDatabase
import es.upsa.mimo.v2021.fitup.utils.extensions.startNewActivity
import es.upsa.mimo.v2021.fitup.model.DBEntities.UserItem
import es.upsa.mimo.v2021.fitup.persistence.PreferencesManager
import es.upsa.mimo.v2021.fitup.ui.MainActivity
import es.upsa.mimo.v2021.fitup.ui.register.RegisterActivity
import es.upsa.mimo.v2021.fitup.utils.extensions.showAlert
import org.koin.androidx.viewmodel.ext.android.viewModel

class StartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartBinding
    private val viewModel: StartViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setup()
    }


    private fun setup() {
        FitUpDatabase.invoke(applicationContext)

        with(viewModel) {
            observe(navigateToHome) { event ->
                event.getContentIfNotHandled()?.let {
                    setPreferences(it)
                    showHome()
                    finish()
                }
            }
            observe(navigateToRegister) { event ->
                event.getContentIfNotHandled()?.let { showRegister() }
            }
            observe(showMessage){ event ->
                event.getContentIfNotHandled()?.let { showAlert(it.title, it.message) }
            }
        }

        binding.button2.setOnClickListener{
            viewModel.onRegisterClicked()
        }

        binding.button1.setOnClickListener{
            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextPassword.text.toString()
            viewModel.onSubmit(email, password)
        }

        val preferences = PreferencesManager(applicationContext)
        if (preferences.darkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            //ActivityCompat.recreate(this)
        }
        viewModel.onLoad(preferences.email, preferences.userToken)
    }

    private fun showRegister() {
        startNewActivity<RegisterActivity>()
    }

    private fun showHome() {
        startNewActivity<MainActivity>()
    }

    private fun setPreferences(user: UserItem) {
        val preferencesManager = PreferencesManager(applicationContext)
        preferencesManager.email = user.email
        preferencesManager.userToken = user.userToken
    }


}