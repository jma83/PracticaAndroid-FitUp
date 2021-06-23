package es.upsa.mimo.v2021.fitup.ui.start

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import es.upsa.mimo.v2021.fitup.databinding.ActivityStartBinding
import es.upsa.mimo.v2021.fitup.ui.home.HomeActivity
import es.upsa.mimo.v2021.fitup.ui.register.RegisterActivity

private lateinit var binding: ActivityStartBinding

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setup()
    }

    private fun setup() {
        binding.button2.setOnClickListener{
            showRegister()
        }

        binding.button1.setOnClickListener{
            showHome()
            /*if (binding.editTextEmail.text.isNotEmpty() && binding.editTextPassword.text.isNotEmpty()) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(binding.editTextEmail.text.toString(), binding.editTextPassword.text.toString()).addOnCompleteListener{
                    if (it.isSuccessful){
                        showHome()
                    }else{
                        showAlert("An error ocurred during authentication process. Please try again later.")
                    }
                }
            }*/
        }
    }

    private fun showAlert(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage(message)
        builder.setPositiveButton("Accept", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showRegister() {
        val registerIntent = Intent(this, RegisterActivity::class.java)

        startActivity(registerIntent)
    }

    private fun showHome() {
        val homeIntent = Intent(this, HomeActivity::class.java).apply {
            putExtra("email", binding.editTextEmail.text.toString())
            putExtra("provider", "email")
        }

        startActivity(homeIntent)
    }

}