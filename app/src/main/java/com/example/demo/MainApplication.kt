package com.example.demo

import android.app.Application
import android.util.Log
import com.blankj.utilcode.util.DeviceUtils

/**
 * @description:
 * @author M&G
 * @date :2023/8/31
 * @version 1.0.0
 */
class MainApplication : Application() {


    companion object {
        lateinit var instance: MainApplication
        fun instance() = instance
    }


    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}