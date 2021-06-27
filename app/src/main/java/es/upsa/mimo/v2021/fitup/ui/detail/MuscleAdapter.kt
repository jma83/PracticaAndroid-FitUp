package es.upsa.mimo.v2021.fitup.ui.detail

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.upsa.mimo.v2021.fitup.R
import es.upsa.mimo.v2021.fitup.databinding.ItemExerciseBinding
import es.upsa.mimo.v2021.fitup.extensions.inflate
import es.upsa.mimo.v2021.fitup.extensions.fromUrl
import es.upsa.mimo.v2021.fitup.model.APIEntities.ExerciseDataSet
import es.upsa.mimo.v2021.fitup.model.APIEntities.Muscle
import kotlin.properties.Delegates

private typealias MuscleListener = (Muscle) -> Unit

class ExerciseAdapter(items: List<Muscle> = emptyList(), private val listener: MuscleListener) :
    RecyclerView.Adapter<ExerciseAdapter.ViewHolder>() {

    var items by Delegates.observable(items) { _, _, _ -> notifyDataSetChanged() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = parent.inflate(R.layout.item_exercise, false)
        return ViewHolder(v, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(view: View, private val listener: MuscleListener) : RecyclerView.ViewHolder(view) {
        private val binding = ItemExerciseBinding.bind(view)

        fun bind(muscle: Muscle) {
            with(binding) {
                exerciseText.text = muscle.name
                val img: String = muscle.image_url_main
                exerciseImage.fromUrl(img)
                root.setOnClickListener { listener(muscle) }
            }
        }
    }

}