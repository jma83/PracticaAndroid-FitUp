package es.upsa.mimo.v2021.fitup.ui.categories

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.upsa.mimo.v2021.fitup.R
import es.upsa.mimo.v2021.fitup.databinding.ItemCategoryBinding
import es.upsa.mimo.v2021.fitup.extensions.inflate
import es.upsa.mimo.v2021.fitup.model.APIEntities.Category
import kotlin.properties.Delegates

private typealias CategoryListener = (Category) -> Unit

class CategoryAdapter(items: List<Category> = emptyList(), private val listener: CategoryListener):
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    var items by Delegates.observable(items) { _, _, _ -> notifyDataSetChanged() }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.ViewHolder {
        val v = parent.inflate(R.layout.item_category, false)
        return ViewHolder(v, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(view: View, private val listener: CategoryListener) :
        RecyclerView.ViewHolder(view) {

        private val binding = ItemCategoryBinding.bind(view)

        fun bind(category: Category) {
            with(binding) {
                categoryName.text = category.name

                root.setOnClickListener { listener(category) }

            }
        }
    }
}