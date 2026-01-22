package com.nikhilkhairnar.quotevault.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.nikhilkhairnar.quotevault.R
import com.nikhilkhairnar.quotevault.adapter.FavoritesAdapter
import com.nikhilkhairnar.quotevault.data.FavoriteRow
import com.nikhilkhairnar.quotevault.data.Quote
import com.nikhilkhairnar.quotevault.databinding.FragmentFavouriteBinding
import com.nikhilkhairnar.quotevault.helper.SupabaseClient
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.launch

class FavoritesFragment : Fragment(R.layout.fragment_favourite) {

    private var _binding: FragmentFavouriteBinding? = null
    private val binding get() = _binding!!
    lateinit var adapter: FavoritesAdapter
    val favorites = mutableListOf<Quote>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFavouriteBinding.bind(view)
        setupRecycler()
        fetchFavorites()
        binding.swipeRefresh.setOnRefreshListener {
            fetchFavorites()
        }
    }

    private fun setupRecycler() {
        adapter = FavoritesAdapter(
            favorites,
            onUnfavorite = { removeFavorite(it) },
            onShare = { shareQuote(it) }
        )
        binding.favoritesRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@FavoritesFragment.adapter
        }
    }

    fun fetchFavorites() {
        binding.swipeRefresh.isRefreshing = true
        val userId = SupabaseClient.auth.currentUserOrNull()?.id
        if (userId == null) {
            binding.swipeRefresh.isRefreshing = false
            return
        }
        lifecycleScope.launch {
            try {
                val favoriteRows = SupabaseClient.client
                    .from("favorites")
                    .select {
                        filter {
                            eq("user_id", userId)
                        }
                    }
                    .decodeList<FavoriteRow>()

                val favoriteIds = favoriteRows.map { it.quote_id }
                favorites.clear()

                if (favoriteIds.isNotEmpty()) {
                    val favQuotes = SupabaseClient.client
                        .from("quotes")
                        .select {
                            filter {
                                isIn("id", favoriteIds)
                            }
                        }
                        .decodeList<Quote>()

                    favorites.addAll(favQuotes)
                }

                adapter.notifyDataSetChanged()

            } catch (e: Exception) {
                e.printStackTrace()
                showSnack("Failed to load favorites")
            } finally {
                binding.swipeRefresh.isRefreshing = false
            }
        }
    }


    fun addFavoriteToList(quote: Quote) {
        // Prevent duplicates
        if (favorites.any { it.id == quote.id }) return
        favorites.add(quote)
        adapter.notifyItemInserted(favorites.size - 1)
    }

    private fun removeFavorite(quote: Quote) {
        val user = SupabaseClient.auth.currentUserOrNull() ?: return

        lifecycleScope.launch {
            try {
                SupabaseClient.client
                    .from("favorites")
                    .delete {
                        filter {
                            eq("user_id", user.id)
                            eq("quote_id", quote.id!!)
                        }
                    }

                val index = favorites.indexOfFirst { it.id == quote.id }
                if (index != -1) {
                    favorites.removeAt(index)
                    adapter.notifyItemRemoved(index)
                }
                showSnack("Removed from favorites")
            } catch (e: Exception) {
                showSnack("Failed to remove")
            }
        }
    }

    private fun showSnack(message: String) {
        view?.let { Snackbar.make(it, message, Snackbar.LENGTH_SHORT).show() }
    }

    private fun shareQuote(quote: Quote) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra("android.intent.extra.TEXT", "\"${quote.text}\"\n— ${quote.author}")
        }
        startActivity(Intent.createChooser(intent, "Share Quote"))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
