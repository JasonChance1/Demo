package com.example.demo.view.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.demo.database.AppDatabase
import com.example.demo.databinding.ActivitySplashBinding
import com.example.demo.util.EasyDataStore
import com.example.demo.view.base.BaseActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {
    private val userDao = AppDatabase.get().userDao()
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            delay(1000)
            startActivity(Intent(this@SplashActivity,LoginActivity::class.java))
        }
//        val username = EasyDataStore.getData("username", "")
//        val password = EasyDataStore.getData("password", "")
//        if (username.isNotEmpty() && password.isNotEmpty()) {
//            userDao.findByUsername(username)?.let {
//                if (password == it.password) {
//                    startActivity(Intent(this, HomeActivity::class.java))
//                } else {
//                    startActivity(Intent(this, LoginActivity::class.java))
//                }
//            }
//        } else {
//            startActivity(Intent(this, LoginActivity::class.java))
//        }
    }
}