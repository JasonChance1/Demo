package com.example.demo.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.demo.database.AppDatabase
import com.example.demo.database.entity.User
import com.example.demo.databinding.ActivityRegisterBinding
import com.example.demo.view.widget.CustomSnackBar

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val userDao = AppDatabase.get().userDao()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        bindEvent()
    }

    private fun bindEvent() {
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.register.setOnClickListener {
            val username = binding.username.text.toString().trim()
            val password = binding.password.text.toString().trim()
            val confirmPassword = binding.confirmPassword.text.toString().trim()

            when {
                username.isEmpty() -> CustomSnackBar(this, "请输入用户名").showError()
                password.isEmpty() -> CustomSnackBar(this, "请输入密码").showError()
                confirmPassword.isEmpty() -> CustomSnackBar(this, "请确认密码").showError()
                password != confirmPassword -> CustomSnackBar(this, "两次密码不一致").showError()
                else -> {
                    userDao.findByUsername(username)?.let {
                        CustomSnackBar(this, "该账号已被注册").showError()
                    } ?: run {
                        userDao.insert(
                            User(
                                0, username, password
                            )
                        )

                        CustomSnackBar(this, "注册成功").showSuccess()
                        finish()
                    }
                }
            }
        }
    }

    private fun initView() {

    }
}