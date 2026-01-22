package com.nikhilkhairnar.quotevault.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.nikhilkhairnar.quotevault.R
import com.nikhilkhairnar.quotevault.helper.SupabaseClient
import com.nikhilkhairnar.quotevault.databinding.ActivitySignupBinding
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.launch
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.signupBtn.setOnClickListener { signUpUser() }
        binding.loginLink.setOnClickListener { finish() }
        setupShowPassword()
    }

    private fun setupShowPassword() {
        binding.hideAndShow.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                binding.passwordInput.transformationMethod =
                    android.text.method.HideReturnsTransformationMethod.getInstance()
                binding.hideAndShow.setImageResource(R.drawable.ic_eye_off) // optional
            } else {
                binding.passwordInput.transformationMethod =
                    android.text.method.PasswordTransformationMethod.getInstance()
                binding.hideAndShow.setImageResource(R.drawable.ic_eye)
            }
            binding.passwordInput.setSelection(binding.passwordInput.text.length)
        }
    }

    private fun signUpUser() {
        val name = binding.nameInput.text.toString().trim()
        val email = binding.emailInput.text.toString().trim()
        val password = binding.passwordInput.text.toString().trim()
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            toast("All fields required")
            return
        }
        lifecycleScope.launch {
            try {
                SupabaseClient.auth.signUpWith(Email) {
                    this.email = email
                    this.password = password
                    data = buildJsonObject {
                        put("full_name", name)
                    }
                }
                startActivity(Intent(this@SignupActivity, LoginActivity::class.java))
                finishAffinity()
            } catch (e: Exception) {
                toast(e.message ?: "Signup failed")
            }
        }
    }

    private fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}
