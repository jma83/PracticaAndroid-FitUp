package es.upsa.mimo.v2021.fitup.fragments.settings

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import es.upsa.mimo.v2021.fitup.R
import es.upsa.mimo.v2021.fitup.databinding.FragmentSettingsBinding
import es.upsa.mimo.v2021.fitup.persistence.PreferencesManager
import es.upsa.mimo.v2021.fitup.ui.settings.SettingsViewModel
import es.upsa.mimo.v2021.fitup.ui.start.StartActivity
import es.upsa.mimo.v2021.fitup.utils.extensions.observe
import es.upsa.mimo.v2021.fitup.utils.extensions.showAlert
import es.upsa.mimo.v2021.fitup.utils.extensions.startNewActivity
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SettingsFragment: Fragment() {

    private val viewModel: SettingsViewModel by sharedViewModel()
    companion object {
        fun newInstance(): SettingsFragment {
            return SettingsFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSettingsBinding.bind(view)
        val preferences = PreferencesManager(requireActivity().applicationContext)

        binding.switchDarkThemeSettings.isChecked = preferences.darkMode
        binding.switchLockDeleteSettings.isChecked = preferences.lockDelete

        binding.switchDarkThemeSettings.setOnCheckedChangeListener { _: CompoundButton, isChecked: Boolean ->
            preferences.darkMode = isChecked
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            requireActivity().recreate();
        }

        with(viewModel) {
            observe(showUserInfo) { event ->
                event.getContentIfNotHandled()?.let {
                    binding.email.text = "Email: ${it.email}"
                    binding.name.text = "Name: ${it.name}"
                    binding.age.text =  "Age: ${it.age}"
                }
            }
            observe(navigateToLogin) {
                removePreferences()
                showLogin()
            }
            observe(showMessage){ event ->
                event.getContentIfNotHandled()?.let { requireActivity().showAlert(it.title, it.message) }
            }
        }

        binding.switchLockDeleteSettings.setOnCheckedChangeListener { _: CompoundButton, isChecked: Boolean ->
            preferences.lockDelete = isChecked
        }

        binding.changePassButton.setOnClickListener {
            val oldPass = binding.editTextPassword.text.toString()
            val newPass = binding.editTextNewPassword.text.toString()
            viewModel.onSubmit(oldPass, newPass)
        }

        binding.logout.setOnClickListener {
            viewModel.navigateToLogin()
        }

        viewModel.onLoad(preferences.email, preferences.userToken)

    }

    private fun showLogin() {
        activity?.startNewActivity<StartActivity>()
    }

    private fun removePreferences() {
        val preferencesManager = PreferencesManager(requireActivity().applicationContext)
        preferencesManager.darkMode = false
        preferencesManager.userToken = null
        preferencesManager.email = null
        preferencesManager.lockDelete = true
    }
}