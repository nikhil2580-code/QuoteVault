package com.nikhilkhairnar.quotevault.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nikhilkhairnar.quotevault.data.QuoteCollection
import com.nikhilkhairnar.quotevault.databinding.ItemCollectionBinding

class CollectionAdapter(
    private val items: List<QuoteCollection>, //One type argument expected for interface Collection<out E>
    private val onClick: (QuoteCollection) -> Unit //
) : RecyclerView.Adapter<CollectionAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemCollectionBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCollectionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.binding.collectionTitle.text = item.name
        holder.binding.collectionCount.text = "${item.quoteCount} Quotes"

        holder.binding.root.setOnClickListener {
            onClick(item)
        }
    }

    override fun getItemCount() = items.size
}
