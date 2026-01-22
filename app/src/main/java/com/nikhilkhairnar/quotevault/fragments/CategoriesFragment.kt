package com.nikhilkhairnar.quotevault.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.nikhilkhairnar.quotevault.adapter.CategoryAdapter
import com.nikhilkhairnar.quotevault.activity.HomeActivity
import com.nikhilkhairnar.quotevault.R
import com.nikhilkhairnar.quotevault.data.Category
import com.nikhilkhairnar.quotevault.databinding.FragmentCategoriesBinding

class CategoriesFragment : Fragment(R.layout.fragment_categories) {
    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CategoryAdapter
    private lateinit var allCategories: List<Category>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentCategoriesBinding.bind(view)
        setupRecycler()
        setupCategories()
        setupSearch()
        setupBack()
    }

    // ---------------- Recycler ----------------

    private fun setupRecycler() {
        adapter = CategoryAdapter { category ->
            openCategory(category.name)
        }
        binding.categoriesRecycler.layoutManager =
            GridLayoutManager(requireContext(), 2)
        binding.categoriesRecycler.adapter = adapter
    }

    // ---------------- Categories Data ----------------

    private fun setupCategories() {
        allCategories = listOf(
            Category("Motivation", R.drawable.motivation),
            Category("Love", R.drawable.love),
            Category("Success", R.drawable.success),
            Category("Wisdom", R.drawable.wisdom),
        )
        adapter.submitList(allCategories)
    }

    // ---------------- Search ----------------

    private fun setupSearch() {
        binding.searchBox.setOnClickListener {
            showSearchDialog()
        }
    }

    private fun showSearchDialog() {
        val input = EditText(requireContext())
        input.hint = "Search category"

        AlertDialog.Builder(requireContext())
            .setTitle("Search Category")
            .setView(input)
            .setPositiveButton("Search") { _, _ ->
                val query = input.text.toString().lowercase()
                val filtered = allCategories.filter {
                    it.name.lowercase().contains(query)
                }
                adapter.submitList(filtered)
            }
            .setNegativeButton("Clear") { _, _ ->
                adapter.submitList(allCategories)
            }
            .show()
    }

    // ---------------- Navigation ----------------

    private fun openCategory(category: String) {
        val intent = Intent(requireContext(), HomeActivity::class.java)
        intent.putExtra("CATEGORY", category)
        startActivity(intent)
    }

    // ---------------- Back ----------------

    private fun setupBack() {
        binding.backBtn.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
