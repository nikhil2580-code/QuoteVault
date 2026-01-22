package com.nikhilkhairnar.quotevault.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.nikhilkhairnar.quotevault.R
import com.nikhilkhairnar.quotevault.adapter.QuoteAdapter
import com.nikhilkhairnar.quotevault.data.Quote
import com.nikhilkhairnar.quotevault.databinding.FragmentHomeBinding
import com.nikhilkhairnar.quotevault.helper.SupabaseClient
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: QuoteAdapter
    private val pageSize = 20
    private var page = 0
    private var isLoading = false
    private var searchQuery: String? = null
    private var selectedCategory: String? = null
    private var currentQod: Quote? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupPullToRefresh()
        setupSearch()
        setupQuoteOfDayButtons()
        setupFab()
        loadQuoteOfTheDay()
        loadQuotes(reset = true)
    }

    private fun setupFab() {
        binding.addQuoteFab.setOnClickListener {
            binding.fragmentContainer.visibility = View.VISIBLE
            parentFragmentManager.beginTransaction()
                .setCustomAnimations(
                    android.R.anim.fade_in,
                    android.R.anim.fade_out,
                    android.R.anim.fade_in,
                    android.R.anim.fade_out
                )
                .replace(R.id.fragmentContainer, CategoriesFragment())
                .addToBackStack("Categories")
                .commit()
        }
    }

    // ---------------- RecyclerView ----------------
    private fun setupRecyclerView() {
        adapter = QuoteAdapter(
            onShare = { shareQuote(it) },
            onFavorite = { saveToFavorites(it) }
        )
        binding.quoteRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.quoteRecycler.adapter = adapter
        binding.quoteRecycler.addOnScrollListener(object :
            androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
            override fun onScrolled(rv: androidx.recyclerview.widget.RecyclerView, dx: Int, dy: Int) {
                if (!rv.canScrollVertically(1) && !isLoading) {
                    loadQuotes()
                }
            }
        })
    }

    // ---------------- Pull To Refresh ----------------
    private fun setupPullToRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            loadQuotes(reset = true)
        }
    }

    // ---------------- Search ----------------
    private fun setupSearch() {
        binding.searchEditText.setOnEditorActionListener { _, _, _ ->
            val input = binding.searchEditText.text.toString()
            searchQuery = input.ifBlank { null }
            loadQuotes(reset = true)
            true
        }
    }

    // ---------------- Quote of the Day Buttons ----------------
    private fun setupQuoteOfDayButtons() {
        binding.qodShare.isEnabled = false
        binding.qodFavorite.isEnabled = false
        binding.qodShare.setOnClickListener {
            currentQod?.let { quote -> shareQuote(quote) } ?: toast("Quote not loaded yet")
        }
        binding.qodFavorite.setOnClickListener {
            currentQod?.let { quote -> saveToFavorites(quote) } ?: toast("Quote not loaded yet")
        }
    }

    // ---------------- Fetch Quotes ----------------
    private fun loadQuotes(reset: Boolean = false) {
        if (reset) {
            adapter.clear()
            page = 0
        }

        isLoading = true
        binding.swipeRefresh.isRefreshing = true
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val quotes = SupabaseClient.client
                    .from("quotes")
                    .select {
                        searchQuery?.let { queryText ->
                            filter {
                                ilike("text", "%$queryText%")
                            }
                        }
                    }
                    .decodeList<Quote>()

                adapter.addQuotes(quotes)

                if (quotes.isEmpty() && reset) showEmptyState() else hideEmptyState()

            } catch (e: Exception) {
                e.printStackTrace()
                toast("Failed to load quotes")
            } finally {
                isLoading = false
                binding.swipeRefresh.isRefreshing = false
            }
        }
    }

    // ---------------- Quote of the Day ----------------
    private fun loadQuoteOfTheDay() {
        binding.quoteOfDayCard.alpha = 0.5f
        binding.qodShare.isEnabled = false
        binding.qodFavorite.isEnabled = false

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val quote = SupabaseClient.client
                    .from("quotes")
                    .select()
                    .decodeList<Quote>()
                    .random()

                if (!isAdded || _binding == null) return@launch
                currentQod = quote
                binding.quoteOfDayCard.visibility = View.VISIBLE
                binding.quoteOfDayText.text = quote.text
                binding.quoteOfDayAuthor.text = "— ${quote.author}"
                binding.qodShare.isEnabled = true
                binding.qodFavorite.isEnabled = true
                binding.quoteOfDayCard.alpha = 1f
            } catch (e: Exception) {
                if (!isAdded || _binding == null) return@launch
                e.printStackTrace()
                binding.quoteOfDayCard.visibility = View.GONE
            }
        }
    }

    // ---------------- Favorites ----------------
    private fun saveToFavorites(quote: Quote) {
        val user = SupabaseClient.auth.currentUserOrNull()
        if (user == null) {
            toast("Login required")
            return
        }
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                SupabaseClient.client
                    .from("favorites")
                    .insert(
                        mapOf(
                            "user_id" to user.id,
                            "quote_id" to quote.id
                        )
                    )
                toast("Added to favorites ❤️")

            } catch (e: Exception) {
                e.printStackTrace()
                toast("Already added")
            }
        }
    }



    // ---------------- Share ----------------
    private fun shareQuote(quote: Quote) {
        val text = "\"${quote.text}\"\n— ${quote.author}"
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, text)
        }

        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(Intent.createChooser(intent, "Share quote"))
        } else {
            toast("No app available to share")
        }
    }

    // ---------------- UI States ----------------
    private fun showEmptyState() {
        binding.emptyState.visibility = View.VISIBLE
        binding.quoteRecycler.visibility = View.GONE
    }

    private fun hideEmptyState() {
        binding.emptyState.visibility = View.GONE
        binding.quoteRecycler.visibility = View.VISIBLE
    }

    // ---------------- Utils ----------------
    private fun toast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


