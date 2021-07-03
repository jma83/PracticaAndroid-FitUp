package es.upsa.mimo.v2021.fitup.ui.trainingLists

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.upsa.mimo.v2021.fitup.R
import es.upsa.mimo.v2021.fitup.databinding.ItemTrainingListBinding
import es.upsa.mimo.v2021.fitup.extensions.inflate
import es.upsa.mimo.v2021.fitup.model.DBEntities.TrainingListItem
import kotlin.properties.Delegates


private typealias TrainingListListener = (TrainingListItem) -> Unit


class TrainingListsAdapter(items: List<TrainingListItem> = emptyList(), private val listener: TrainingListListener):
    RecyclerView.Adapter<TrainingListsAdapter.ViewHolder>() {

    var items by Delegates.observable(items) { _, _, _ -> notifyDataSetChanged() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = parent.inflate(R.layout.item_training_list, false)
        return ViewHolder(v, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }


    class ViewHolder(view: View, private val listener: TrainingListListener) :
        RecyclerView.ViewHolder(view) {

        private val binding = ItemTrainingListBinding.bind(view)

        fun bind(trainingListItem: TrainingListItem) {
            with(binding) {
                this.trainingListName.text = trainingListItem.name
                this.trainingListDate.text = trainingListItem.creationDate.toString()

                root.setOnClickListener { listener(trainingListItem) }

            }
        }
    }

    override fun getItemCount(): Int = items.size
}