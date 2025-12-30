package com.ext.linkagerecyclerview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ext.linkagerecyclerview.model.Category

class CategoryAdapter(
    private val categories: List<Category>,
    private val onClick: (position: Int) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var selectedPosition = 0

    fun setSelected(position: Int) {
        val old = selectedPosition
        selectedPosition = position
        notifyItemChanged(old)
        notifyItemChanged(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position], position == selectedPosition)
    }

    override fun getItemCount(): Int = categories.size

    inner class CategoryViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private val title: TextView = itemView.findViewById(android.R.id.text1)

        fun bind(category: Category, selected: Boolean) {
            title.text = category.title
            title.isSelected = selected

            itemView.setOnClickListener {
                onClick(adapterPosition)
            }
        }
    }
}
