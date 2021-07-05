package es.upsa.mimo.v2021.fitup.ui.trainingLists.add

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.upsa.mimo.v2021.fitup.R
import es.upsa.mimo.v2021.fitup.databinding.ItemTrainingListAddBinding
import es.upsa.mimo.v2021.fitup.databinding.ItemTrainingListBinding
import es.upsa.mimo.v2021.fitup.extensions.inflate
import es.upsa.mimo.v2021.fitup.model.DBEntities.TrainingListItem
import kotlin.properties.Delegates


private typealias TrainingListListener = (TrainingListItem) -> Unit


class AddToTrainingListsAdapter(items: List<TrainingListItem> = emptyList(), private val listener: TrainingListListener):
    RecyclerView.Adapter<AddToTrainingListsAdapter.ViewHolder>() {

    var items by Delegates.observable(items) { _, _, _ -> notifyDataSetChanged() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = parent.inflate(R.layout.item_training_list_add, false)
        return ViewHolder(v, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }


    class ViewHolder(view: View, private val listener: TrainingListListener) :
        RecyclerView.ViewHolder(view) {

        private val binding = ItemTrainingListAddBinding.bind(view)

        fun bind(trainingListItem: TrainingListItem) {
            with(binding) {
                trainingListName.text = trainingListItem.name
                trainingListDate.text = trainingListItem.creationDate.toString()

                switchTrainingList.setOnClickListener { listener(trainingListItem) }

            }
        }
    }

    override fun getItemCount(): Int = items.size
}