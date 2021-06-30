package es.upsa.mimo.v2021.fitup.ui.main

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import es.upsa.mimo.v2021.fitup.R
import es.upsa.mimo.v2021.fitup.databinding.ActivityMainBinding
import es.upsa.mimo.v2021.fitup.fragments.CategoriesFragment
import es.upsa.mimo.v2021.fitup.fragments.HomeFragment
import es.upsa.mimo.v2021.fitup.fragments.MyListsFragment

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
            selectFragment(it)
            true
        }

        binding.navigation.selectedItemId = R.id.navigation_home
    }

    private fun selectFragment(it: MenuItem) {

        val fragmentClicked: Fragment

        when (it.itemId) {
            R.id.navigation_home -> fragmentClicked = HomeFragment.newInstance()
            R.id.navigation_categories -> fragmentClicked = CategoriesFragment.newInstance()
            else -> fragmentClicked = MyListsFragment.newInstance()
        }

        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.flMainContent, fragmentClicked)
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        ft.commit()

    }
}