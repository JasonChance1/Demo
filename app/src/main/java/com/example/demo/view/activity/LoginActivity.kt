package com.example.demo.view.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import com.example.demo.database.AppDatabase
import com.example.demo.database.entity.User
import com.example.demo.databinding.ActivityLoginBinding
import com.example.demo.util.EasyDataStore
import com.example.demo.util.PermissionUtil
import com.example.demo.util.showError
import com.example.demo.util.showSuccess
import com.example.demo.view.base.BaseActivity

class LoginActivity : BaseActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val userDao = AppDatabase.get().userDao()
    private val permissionUtil = PermissionUtil()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        bindEvent()
    }

    private fun initView() {
        if(!permissionUtil.isExternalStoragePermission()){
            permissionUtil.requestExternalStoragePermission()
        }

        if(!permissionUtil.isStorageManagerPermission()){
            permissionUtil.requestStorageManagerPermission(this)
        }
        if(!permissionUtil.isAlertPermission()){
            permissionUtil.requestAlertPermission()
        }

        if (!Settings.canDrawOverlays(this)) {
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
            startActivity(intent) // 使用startActivity()替代过时的startActivityForResult()，如果你不需要在Activity返回时接收结果
        }

        val username = EasyDataStore.getData("username", "")
        val password = EasyDataStore.getData("password", "")
        if (EasyDataStore.getData("autoLogin", false)) {
            binding.username.setText(username)
            binding.password.setText(password)
            binding.remember.isChecked = true
            binding.autoLogin.isChecked = true
            if (username.isNotEmpty() && password.isNotEmpty()) {
                userDao.findByUsername(username)?.let {
                    if (password == it.password) {
                        startActivity(Intent(this, HomeActivity::class.java))
                    }
                }
            }
        } else if (EasyDataStore.getData("remember", false)) {
            binding.username.setText(username)
            binding.password.setText(password)
            binding.remember.isChecked = true
        }
    }

    private fun bindEvent() {
        binding.login.setOnClickListener {
            val username = binding.username.text.toString().trim()
            val password = binding.password.text.toString().trim()
            when {
                username.isEmpty() -> this.showError("请输入用户名")
                password.isEmpty() -> this.showError("请输入密码")
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
                            this.showSuccess("登录成功")
                            startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                            finish()
                        } else {
                            this.showError("请检查用户名密码是否匹配")
                        }
                    } ?: kotlin.run {
                        this.showError("该账号尚未注册")
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
                username.isEmpty() -> this.showError("请输入用户名")
                password.isEmpty() -> this.showError("请输入密码")
                confirmPassword.isEmpty() -> this.showError("请确认密码")
                password != confirmPassword -> this.showError("两次密码不一致")
                else -> {
                    userDao.findByUsername(username)?.let {
                        this.showError("该账号已被注册")
                    } ?: run {
                        userDao.insert(
                            User(
                                0, username, password
                            )
                        )
                        this.showSuccess("注册成功")
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