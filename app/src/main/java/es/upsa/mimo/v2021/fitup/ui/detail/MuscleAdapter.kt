package es.upsa.mimo.v2021.fitup.ui.detail

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.upsa.mimo.v2021.fitup.R
import es.upsa.mimo.v2021.fitup.databinding.ItemMuscleBinding
import es.upsa.mimo.v2021.fitup.extensions.inflate
import es.upsa.mimo.v2021.fitup.extensions.fromUrl
import es.upsa.mimo.v2021.fitup.model.APIEntities.Muscle
import kotlin.properties.Delegates

class MuscleAdapter(items: List<Muscle> = emptyList()) :
    RecyclerView.Adapter<MuscleAdapter.ViewHolder>() {
    var items by Delegates.observable(items) { _, _, _ -> notifyDataSetChanged() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = parent.inflate(R.layout.item_muscle, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemMuscleBinding.bind(view)

        fun bind(muscle: Muscle) {
            with(binding) {
                muscleText.text = muscle.name
                val endpointImg = "https://wger.de"

                val img: String = endpointImg + muscle.image_url_main
                muscleImage.fromUrl(img)
            }
        }
    }

}