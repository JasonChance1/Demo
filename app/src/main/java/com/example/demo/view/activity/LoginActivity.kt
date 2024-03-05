package com.example.demo.view.activity

import android.content.Intent
import android.os.Bundle
import com.example.demo.database.AppDatabase
import com.example.demo.database.entity.User
import com.example.demo.databinding.ActivityLoginBinding
import com.example.demo.util.EasyDataStore
import com.example.demo.util.HttpUtil
import com.example.demo.view.base.BaseActivity
import com.example.demo.view.widget.CustomSnackBar

class LoginActivity : BaseActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val userDao = AppDatabase.get().userDao()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        bindEvent()
//        binding.login.setOnClickListener {
//            if (EasyDataStore.getData("theme", R.style.AppTheme_Night) == R.style.AppTheme_Night) {
//                EasyDataStore.putData("theme", R.style.AppTheme)
//            } else {
//                EasyDataStore.putData("theme", R.style.AppTheme_Night)
//            }
//            recreate()
//        }
    }

    private fun initView() {

    }

    private fun bindEvent() {
        binding.login.setOnClickListener {
            val username = binding.username.text.toString().trim()
            val password = binding.password.text.toString().trim()
            when {
                username.isEmpty() -> CustomSnackBar(this, "请输入用户名").showError()
                password.isEmpty() -> CustomSnackBar(this, "请输入密码").showError()
                else -> {
                    userDao.findByUsername(username)?.let {
                        if (password == it.password) {
                            if (binding.autoLogin.isChecked) {
                                EasyDataStore.putData("password", password)
                                EasyDataStore.putData("username", username)
                                EasyDataStore.putData("autoLogin", true)
                            } else if (binding.remember.isChecked) {
                                EasyDataStore.putData("password", password)
                                EasyDataStore.putData("username", username)
                                EasyDataStore.putData("remember", true)
                            }
                            startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                            finish()
                        } else {
                            CustomSnackBar(this, "请检查用户名密码是否匹配").showError()
                        }
                    } ?: kotlin.run {
                        CustomSnackBar(this, "该账号尚未注册").show()
                    }
                }

            }
//            HttpUtil.getUser()
        }

        binding.toRegister.setOnClickListener {
            binding.transformationLayout.startTransform()
        }

        binding.toolbar.setNavigationOnClickListener {
            binding.transformationLayout.finishTransform()
        }

        binding.register.setOnClickListener {
            val username = binding.registerUsername.text.toString().trim()
            val password = binding.registerPassword.text.toString().trim()
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
                        binding.transformationLayout.finishTransform()
                        binding.username.setText(username)
                        binding.password.setText(password)
                    }
                }
            }
        }

        binding.skip.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

        binding.autoLogin.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) binding.remember.isChecked = true
        }

        binding.remember.setOnCheckedChangeListener { buttonView, isChecked ->
            if (!isChecked && binding.autoLogin.isChecked) {
                binding.autoLogin.isChecked = false
            }
        }
    }

}