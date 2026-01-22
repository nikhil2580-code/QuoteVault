package com.nikhilkhairnar.quotevault.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nikhilkhairnar.quotevault.data.Quote
import com.nikhilkhairnar.quotevault.databinding.ItemQuoteBinding
import com.nikhilkhairnar.quotevault.helper.SettingsPrefs

class QuoteAdapter(
    private val onShare: (Quote) -> Unit,
    private val onFavorite: (Quote) -> Unit
) : RecyclerView.Adapter<QuoteAdapter.ViewHolder>() {

    private val quotes = mutableListOf<Quote>()

    fun submitList(newQuotes: List<Quote>, clear: Boolean = false) {
        if (clear) quotes.clear()
        quotes.addAll(newQuotes)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemQuoteBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemQuoteBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val quote = quotes[position]

        holder.binding.apply {
            quoteText.text = quote.text
            authorText.text = "— ${quote.author}"

            val fontSize = SettingsPrefs.getFontSize(holder.itemView.context)
            holder.binding.quoteText.textSize = fontSize.toFloat()

            shareIcon.setOnClickListener { onShare(quote) }
            favoriteIcon.setOnClickListener { onFavorite(quote) }
        }
    }

    override fun getItemCount() = quotes.size

    fun addQuotes(newQuotes: List<Quote>) {
        val start = quotes.size
        quotes.addAll(newQuotes)
        notifyItemRangeInserted(start, newQuotes.size)
    }

    fun clear() {
        quotes.clear()
        notifyDataSetChanged()
    }
}