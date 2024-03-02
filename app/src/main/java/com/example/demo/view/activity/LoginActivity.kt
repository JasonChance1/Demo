package com.example.demo.view.activity

import android.content.Intent
import android.os.Bundle
import com.example.demo.database.AppDatabase
import com.example.demo.databinding.ActivityLoginBinding
import com.example.demo.util.EasyDataStore
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
                            if(binding.autoLogin.isChecked){
                                EasyDataStore.putData("password", password)
                                EasyDataStore.putData("username",username)
                                EasyDataStore.putData("autoLogin",true)
                            }else if (binding.remember.isChecked){
                                EasyDataStore.putData("password", password)
                                EasyDataStore.putData("username",username)
                                EasyDataStore.putData("remember",true)
                            }
                            startActivity(Intent(this@LoginActivity,HomeActivity::class.java))
                        }else{
                            CustomSnackBar(this, "请检查用户名密码是否匹配").showError()
                        }
                    }?: kotlin.run {
                        CustomSnackBar(this,"该账号尚未注册").show()
                    }
                }

            }
        }
    }

}