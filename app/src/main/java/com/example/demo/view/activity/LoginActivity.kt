package com.example.demo.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.demo.R
import com.example.demo.databinding.ActivityLoginBinding
import com.example.demo.util.EasyDataStore

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        val theme = EasyDataStore.getData("theme",R.style.AppTheme)
        setTheme(theme)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.login.setOnClickListener {
            if (EasyDataStore.getData("theme", R.style.AppTheme_Night) == R.style.AppTheme_Night) {
                EasyDataStore.putData("theme", R.style.AppTheme)
            } else {
                EasyDataStore.putData("theme", R.style.AppTheme_Night)
            }
            recreate()
        }

    }
}