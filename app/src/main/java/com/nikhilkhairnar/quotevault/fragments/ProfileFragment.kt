package com.nikhilkhairnar.quotevault.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.nikhilkhairnar.quotevault.activity.LoginActivity
import com.nikhilkhairnar.quotevault.R
import com.nikhilkhairnar.quotevault.helper.UserSession
import com.nikhilkhairnar.quotevault.databinding.FragmentProfileBinding
import com.nikhilkhairnar.quotevault.helper.SupabaseClient
import kotlinx.coroutines.launch

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProfileBinding.bind(view)
        loadUserData()
        setupBack()
        setupLogout()
    }

    private fun loadUserData() {
        binding.tvUsername.text =
            UserSession.getUsername(requireContext())

        binding.imgAvatar.setImageResource(
            UserSession.getAvatar(requireContext())
        )
    }

    private fun setupBack() {
        var function: (View) -> Unit = {
            requireActivity()
                .onBackPressedDispatcher
                .onBackPressed()
        }
        binding.btnBack.setOnClickListener(function)
    }

    private fun setupLogout() {
        binding.btnLogout.setOnClickListener {
            lifecycleScope.launch {
                SupabaseClient.auth.signOut() // 🔥 CLEAR SUPABASE SESSION
                UserSession.logout(requireContext())

                val intent = Intent(requireContext(), LoginActivity::class.java)
                intent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or
                            Intent.FLAG_ACTIVITY_CLEAR_TASK

                startActivity(intent)
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
