package com.nikhilkhairnar.quotevault.fragments


import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.nikhilkhairnar.quotevault.R
import com.nikhilkhairnar.quotevault.databinding.FragmentQuoteDetailBinding

class QuoteDetailFragment : Fragment(R.layout.fragment_quote_detail) {
    private var _binding: FragmentQuoteDetailBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentQuoteDetailBinding.bind(view)
        loadQuote()
        setupActions()
    }

    private fun loadQuote() {
        val quoteText = arguments?.getString("quote_text") ?: "No quote found"
        val quoteAuthor = arguments?.getString("quote_author") ?: "Unknown"
        binding.quoteText.text = quoteText
        binding.authorText.text = "— $quoteAuthor"
    }

    private fun setupActions() {
        binding.quoteText.setOnLongClickListener {
            shareQuote()
            true
        }
    }

    private fun shareQuote() {
        val text = "${binding.quoteText.text}\n${binding.authorText.text}"
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, text)
        }
        startActivity(Intent.createChooser(intent, "Share Quote"))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
