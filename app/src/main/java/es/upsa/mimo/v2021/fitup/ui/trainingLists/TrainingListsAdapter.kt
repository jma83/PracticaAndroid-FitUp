package es.upsa.mimo.v2021.fitup.ui.trainingLists

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.upsa.mimo.v2021.fitup.R
import es.upsa.mimo.v2021.fitup.databinding.ItemTrainingListBinding
import es.upsa.mimo.v2021.fitup.utils.extensions.inflate
import es.upsa.mimo.v2021.fitup.model.DBEntities.TrainingListItem
import es.upsa.mimo.v2021.fitup.persistence.PreferencesManager
import es.upsa.mimo.v2021.fitup.utils.Constants
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.coroutineContext
import kotlin.properties.Delegates


private typealias TrainingListListener = (TrainingListItem, Boolean) -> Unit


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

        private val preferences = PreferencesManager(view.context)
        private val binding = ItemTrainingListBinding.bind(view)

        fun bind(trainingListItem: TrainingListItem) {
            with(binding) {
                trainingListName.text = trainingListItem.name
                if (trainingListItem.creationDate != null) {
                    val currentDate: String = SimpleDateFormat(Constants.DATE_TIME_FORMAT, Locale.getDefault()).format(trainingListItem.creationDate!!)
                    trainingListDate.text = currentDate
                }

                if (!preferences.lockDelete) {
                    deleteButton.visibility = View.VISIBLE
                }
                deleteButton.setOnClickListener{ listener(trainingListItem, true) }
                root.setOnClickListener { listener(trainingListItem, false) }

            }
        }
    }

    override fun getItemCount(): Int = items.size
}