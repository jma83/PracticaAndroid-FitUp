package es.upsa.mimo.v2021.fitup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import es.upsa.mimo.v2021.fitup.databinding.ActivityMainBinding

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}