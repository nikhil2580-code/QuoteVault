package com.nikhilkhairnar.quotevault.fragments

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.nikhilkhairnar.quotevault.data.QuoteCollection
import com.nikhilkhairnar.quotevault.R
import com.nikhilkhairnar.quotevault.adapter.CollectionAdapter
import com.nikhilkhairnar.quotevault.databinding.FragmentCollectionsBinding
import java.util.UUID

class CollectionsFragment : Fragment(R.layout.fragment_collections) {

    private var _binding: FragmentCollectionsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CollectionAdapter
    private val collections = mutableListOf<QuoteCollection>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCollectionsBinding.bind(view)
        setupRecycler()
        loadCollections()
        binding.fabAdd.setOnClickListener {
            showCreateCollectionDialog()
        }
    }

    private fun setupRecycler() {
        adapter = CollectionAdapter(collections) { collection ->
            openCollection(collection)
        }
        binding.collectionsRecycler.layoutManager =  //Unresolved reference: categoriesRecycler
            LinearLayoutManager(requireContext())
        binding.collectionsRecycler.adapter = adapter
    }


    private fun loadCollections() {
        collections.clear()
        collections.addAll(
            listOf(
                QuoteCollection("1", "Morning Motivation", 12),
                QuoteCollection("2", "Stoicism", 24),
                QuoteCollection("3", "Work Quotes", 9)
            )
        )
        adapter.notifyDataSetChanged()
    }

    private fun showCreateCollectionDialog() {
        val input = EditText(requireContext())
        input.hint = "Collection name"

        AlertDialog.Builder(requireContext())
            .setTitle("New Collection")
            .setView(input)
            .setPositiveButton("Create") { _, _ ->
                val name = input.text.toString()
                if (name.isNotBlank()) {
                    createCollection(name)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun createCollection(name: String) {
        val newCollection = QuoteCollection(
            id = UUID.randomUUID().toString(),
            name = name,
            quoteCount = 0
        )
        collections.add(0, newCollection)
        adapter.notifyItemInserted(0)

        // Later → save to Supabase
    }

    private fun openCollection(collection: QuoteCollection) {
        // Navigate to CollectionQuotesFragment
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
