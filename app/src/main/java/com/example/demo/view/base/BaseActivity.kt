package com.example.demo.view.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.demo.R
import com.example.demo.util.EasyDataStore

/**
 * @description:
 * @author yanglei
 * @date :2024/3/2
 * @version 1.0.0
 */
open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // 从EasyDataStore获取当前设置的主题
        val theme = EasyDataStore.getData("theme", R.style.AppTheme)
        // 设置当前Activity的主题
        setTheme(theme)
        // 调用super.onCreate来完成Activity的初始化
        super.onCreate(savedInstanceState)
    }
}