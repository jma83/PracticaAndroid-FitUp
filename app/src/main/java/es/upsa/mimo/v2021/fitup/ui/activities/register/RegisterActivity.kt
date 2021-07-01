package es.upsa.mimo.v2021.fitup.ui.activities.register

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import es.upsa.mimo.v2021.fitup.databinding.ActivityRegisterBinding
import es.upsa.mimo.v2021.fitup.extensions.startActivity1
import es.upsa.mimo.v2021.fitup.ui.MainActivity


private lateinit var binding: ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setup()
    }

    fun setup() {
        binding.button1.setOnClickListener {
            if (binding.editTextEmail.text.isNotEmpty() && binding.editTextPassword.text.isNotEmpty()) {
                Log.d("upsa.mimo.v2021.fitup", binding.editTextEmail.text.toString())
                Log.d("upsa.mimo.v2021.fitup", binding.editTextPassword.text.toString())
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(binding.editTextEmail.text.toString(), binding.editTextPassword.text.toString()).addOnCompleteListener {

                    if (!it.isSuccessful) {
                        showHome()
                        try {
                            throw it.getException()!!
                        } catch (e: FirebaseAuthException) {
                            showAlert(e.message ?: "Error")

                        } catch (e: Exception) {
                            Log.e("upsa.mimo.v2021.fitup", e.message!!)
                        }
                    }
                }
            } else {
                showAlert("Fields must be filled")
            }
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

    private fun showHome() {
        startActivity1<MainActivity>(
            MainActivity.EXTRA_EMAIL to "email")
    }

}