package com.example.demo.view.activity

import android.os.Bundle
import com.example.demo.R
import com.example.demo.databinding.ActivityLoginBinding
import com.example.demo.util.EasyDataStore
import com.example.demo.view.base.BaseActivity

class LoginActivity : BaseActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
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