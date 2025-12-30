package com.ext.linkagerecyclerview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ext.linkagerecyclerview.model.LinkageItem

class ContentAdapter(
    private val items: List<LinkageItem>
) : RecyclerView.Adapter<ContentAdapter.ContentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)
        return ContentViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ContentViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private val title: TextView = itemView.findViewById(android.R.id.text1)

        fun bind(item: LinkageItem) {
            title.text = item.title
        }
    }
}
