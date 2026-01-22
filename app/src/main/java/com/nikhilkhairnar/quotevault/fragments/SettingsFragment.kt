package com.nikhilkhairnar.quotevault.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.nikhilkhairnar.quotevault.helper.SettingsPrefs
import com.nikhilkhairnar.quotevault.databinding.FragmentSettingBinding


class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadSettings()
        setupDarkMode()
        setupFontSize()
        setupThemeToggle()
        setupBack()
    }

    private fun loadSettings() {
        binding.switchDarkMode.isChecked =
            SettingsPrefs.isDarkMode(requireContext())

        binding.seekFontSize.progress =
            SettingsPrefs.getFontSize(requireContext())
    }

    private fun setupDarkMode() {
            binding.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            SettingsPrefs.saveDarkMode(requireContext(), isChecked)

            AppCompatDelegate.setDefaultNightMode(
                if (isChecked)
                    AppCompatDelegate.MODE_NIGHT_YES
                else
                    AppCompatDelegate.MODE_NIGHT_NO
            )
        }
    }

    private fun setupFontSize() {
        binding.seekFontSize.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    SettingsPrefs.saveFontSize(
                        requireContext(),
                        progress.coerceAtLeast(14)
                    )
                }
                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            }
        )
    }

    private fun setupThemeToggle() {
        binding.tvTitle.setOnClickListener {
            val current = SettingsPrefs.getTheme(requireContext())
            val newTheme = if (current == "blue") "green" else "blue"
            SettingsPrefs.saveTheme(requireContext(), newTheme)
            requireActivity().recreate()
        }
    }

    private fun setupBack() {
        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

