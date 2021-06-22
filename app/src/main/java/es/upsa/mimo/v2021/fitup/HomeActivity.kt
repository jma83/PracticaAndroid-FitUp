package es.upsa.mimo.v2021.fitup

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import es.upsa.mimo.v2021.fitup.databinding.FragmentHomeBinding
import es.upsa.mimo.v2021.fitup.extensions.observe
import es.upsa.mimo.v2021.fitup.viewmodels.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModel()
    val exerciseAdapter by lazy { ExerciseAdapter { viewModel.onItemClicked(it) } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        with(viewModel) {
            observe(items) {
                exerciseAdapter.items = it
            }
            observe(navigateToDetail) { event ->
                event.getContentIfNotHandled()?.let { navigateToDetail(it) }
            }
        }

        binding.recyclerHome.adapter = exerciseAdapter
        viewModel.onLoad()
        setup()
    }

    private fun setup() {
        title = "Home"
        val bundle = intent.extras
        val email = bundle?.getString("email")
        val provider = bundle?.getString("provider")

        binding.textField1.text = email
        binding.textField2.text = provider

        //logoutButto{ FirebaseAuth.getInstance().signOut() }
    }


    private fun navigateToDetail(id: Int) {
        // startActivity<DetailActivity>(DetailActivity.EXTRA_ID to id)
    }
}