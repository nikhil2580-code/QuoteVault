package com.nikhilkhairnar.quotevault.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.nikhilkhairnar.quotevault.R
import com.nikhilkhairnar.quotevault.databinding.ActivityLoginBinding
import com.nikhilkhairnar.quotevault.helper.SupabaseClient
import com.nikhilkhairnar.quotevault.helper.UserSession
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.loginBtn.setOnClickListener { loginUser() }
        binding.signup.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
        setupShowPassword()
    }

    private fun loginUser() {
        val email = binding.emailInput.text.toString().trim()
        val password = binding.passwordInput.text.toString().trim()
        if (email.isEmpty() || password.isEmpty()) {
            toast("Please fill all fields")
            return
        }
        lifecycleScope.launch {
            try {
                SupabaseClient.auth.signInWith(Email) {
                    this.email = email
                    this.password = password
                }
                UserSession.login(this@LoginActivity, email.substringBefore("@"))
                startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                finish()
            } catch (e: Exception) {
                toast(e.message ?: "Login failed")
            }
        }
    }

    private fun setupShowPassword() {
        binding.showPass.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                binding.passwordInput.transformationMethod =
                    android.text.method.HideReturnsTransformationMethod.getInstance()
                binding.showPass.setImageResource(R.drawable.ic_eye_off) // optional
            } else {
                binding.passwordInput.transformationMethod =
                    android.text.method.PasswordTransformationMethod.getInstance()
                binding.showPass.setImageResource(R.drawable.ic_eye)
            }
            binding.passwordInput.setSelection(binding.passwordInput.text.length)
        }
    }


    private fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}
