package es.upsa.mimo.v2021.fitup.ui.exercises

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.upsa.mimo.v2021.fitup.R
import es.upsa.mimo.v2021.fitup.databinding.ItemExerciseBinding
import es.upsa.mimo.v2021.fitup.utils.extensions.inflate
import es.upsa.mimo.v2021.fitup.utils.extensions.fromUrl
import es.upsa.mimo.v2021.fitup.model.APIEntities.ExerciseDataSet
import kotlin.properties.Delegates

private typealias ExerciseDataSetListener = (ExerciseDataSet) -> Unit

class ExerciseAdapter(items: List<ExerciseDataSet> = emptyList(), private val listener: ExerciseDataSetListener) :
            RecyclerView.Adapter<ExerciseAdapter.ViewHolder>() {

    var items by Delegates.observable(items) { _, _, _ -> notifyDataSetChanged() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = parent.inflate(R.layout.item_exercise, false)
        return ViewHolder(
            v,
            listener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(view: View, private val listener: ExerciseDataSetListener) :
            RecyclerView.ViewHolder(view) {

        private val binding = ItemExerciseBinding.bind(view)

        fun bind(exerciseDataSet: ExerciseDataSet) {
            with(binding) {
                exerciseText.text = exerciseDataSet.exerciseInfo.name
                val img: String? = exerciseDataSet.exerciseImage?.image
                if (img != null) {
                    exerciseImage.fromUrl(img)
                }

                root.setOnClickListener { listener(exerciseDataSet) }

            }
        }
    }

}