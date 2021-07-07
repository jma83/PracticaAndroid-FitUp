package es.upsa.mimo.v2021.fitup.ui.trainingLists.add

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.upsa.mimo.v2021.fitup.R
import es.upsa.mimo.v2021.fitup.databinding.ItemTrainingListAddBinding
import es.upsa.mimo.v2021.fitup.utils.extensions.inflate
import kotlin.properties.Delegates


private typealias TrainingListListener = (TrainingListItemActive, Boolean) -> Unit


class AddToTrainingListsAdapter(items: List<TrainingListItemActive> = emptyList(), private val listener: TrainingListListener):
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

        fun bind(trainingListItem: TrainingListItemActive) {
            with(binding) {
                trainingListName.text = trainingListItem.trainingListItem.name
                trainingListDate.text = trainingListItem.trainingListItem.creationDate.toString()
                switchTrainingList.isChecked = trainingListItem.active

                switchTrainingList.setOnCheckedChangeListener { _, isChecked ->
                    listener(trainingListItem, isChecked)
                }

            }
        }
    }

    override fun getItemCount(): Int = items.size
}