package es.upsa.mimo.v2021.fitup.fragments.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import es.upsa.mimo.v2021.fitup.R
import es.upsa.mimo.v2021.fitup.databinding.FragmentSettingsBinding
import es.upsa.mimo.v2021.fitup.persistence.PreferencesManager

class SettingsFragment: Fragment() {
    companion object {
        fun newInstance(): SettingsFragment {
            return SettingsFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSettingsBinding.bind(view)
        binding.switchDarkThemeSettings.setOnCheckedChangeListener { buttonView: CompoundButton, isChecked: Boolean ->
            PreferencesManager(requireActivity().applicationContext).darkMode = isChecked
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                ActivityCompat.recreate(requireActivity())
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                ActivityCompat.recreate(requireActivity())
            }
        }

        binding.switchLockDeleteSettings.setOnCheckedChangeListener { buttonView: CompoundButton, isChecked: Boolean ->
            PreferencesManager(requireActivity().applicationContext).lockDelete = isChecked
        }
    }
}