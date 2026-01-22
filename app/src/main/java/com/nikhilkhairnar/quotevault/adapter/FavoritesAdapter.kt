package com.nikhilkhairnar.quotevault.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nikhilkhairnar.quotevault.data.Quote
import com.nikhilkhairnar.quotevault.databinding.ItemFavoriteQuoteBinding

class FavoritesAdapter(
    private val list: MutableList<Quote>,
    private val onUnfavorite: (Quote) -> Unit,
    private val onShare: (Quote) -> Unit
) : RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {
    inner class ViewHolder(
        private val binding: ItemFavoriteQuoteBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(quote: Quote) {
            binding.quoteText.text = quote.text
            binding.authorText.text = "— ${quote.author}"
            binding.heart.setOnClickListener { onUnfavorite(quote) }
            binding.share.setOnClickListener { onShare(quote) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFavoriteQuoteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }
    override fun getItemCount(): Int = list.size
}
