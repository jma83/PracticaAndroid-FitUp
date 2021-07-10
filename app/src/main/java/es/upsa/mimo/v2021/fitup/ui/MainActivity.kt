package es.upsa.mimo.v2021.fitup.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import es.upsa.mimo.v2021.fitup.R
import es.upsa.mimo.v2021.fitup.databinding.ActivityMainBinding
import es.upsa.mimo.v2021.fitup.fragments.categories.CategoriesFragment
import es.upsa.mimo.v2021.fitup.fragments.home.HomeFragment
import es.upsa.mimo.v2021.fitup.fragments.trainingLists.TrainingListsFragment
import es.upsa.mimo.v2021.fitup.fragments.settings.SettingsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    companion object {
        const val EXTRA_EMAIL = "HomeActivity:email"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navigation.setOnNavigationItemSelectedListener {
            selectFragment(it.itemId)
            true
        }

        selectFragment(binding.navigation.selectedItemId)
    }

    private fun selectFragment(itemId: Int) {

        val fragmentClicked: Fragment

        when (itemId) {
            R.id.navigation_home -> fragmentClicked = HomeFragment.newInstance()
            R.id.navigation_categories -> fragmentClicked = CategoriesFragment.newInstance()
            R.id.navigation_lists -> fragmentClicked = TrainingListsFragment.newInstance()
            R.id.navigation_settings -> fragmentClicked = SettingsFragment.newInstance()
            else -> fragmentClicked = SettingsFragment.newInstance()
        }

        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.flMainContent, fragmentClicked)
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        ft.commit()

    }
}