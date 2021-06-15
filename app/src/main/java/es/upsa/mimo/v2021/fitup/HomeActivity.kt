package es.upsa.mimo.v2021.fitup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import es.upsa.mimo.v2021.fitup.databinding.FragmentHomeBinding

private lateinit var binding: FragmentHomeBinding

enum class ProviderType {
    BASIC
}


class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras
        val email = bundle?.getString("email")
        val provider = bundle?.getString("provider")
        setup(email ?: "", provider ?: "")
    }

    private fun setup(email: String, provider: String) {
        title = "Home"

        binding.textField1.text = email
        binding.textField2.text = provider

        //logoutButto{ FirebaseAuth.getInstance().signOut() }
    }

}