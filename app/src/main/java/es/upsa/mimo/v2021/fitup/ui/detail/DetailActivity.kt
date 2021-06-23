package es.upsa.mimo.v2021.fitup.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import es.upsa.mimo.v2021.fitup.databinding.ActivityDetailBinding
import es.upsa.mimo.v2021.fitup.extensions.fromUrl
import es.upsa.mimo.v2021.fitup.extensions.observe
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity: AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "DetailActivity:extraId"
    }

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(viewModel) {
            observe(item) {
                supportActionBar?.title = it.exerciseInfo.name
                var img = it.exerciseImage?.image
                if (img == null) {
                    img = "https://www.vippng.com/png/detail/221-2210873_aerobic-exercise-icon.png"
                }
                binding.detailThumb.fromUrl(img)
            }
        }

        viewModel.onCreate(intent.getIntExtra(EXTRA_ID, 0))
    }
}