package com.example.demo.view.widget

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.PixelFormat
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.demo.MainApplication
import com.example.demo.R
import java.lang.ref.WeakReference

/**
 * @description:消息提示，图标、提示布局中的颜色需要设置为明确的颜色值，不能用attr进行设置，否则图标或背景可能会无法正常显示
 * @author yanglei
 * @date :2024/3/8
 * @version 1.0.0
 */
class GlobalMessageManager(context: Context) {
    private var isViewAttached: Boolean = false // 新增一个标记来记录视图是否被添加
    private val contextRef = WeakReference(context)
    private var currentView: View? = null
    private val wm: WindowManager =
        MainApplication.instance.getSystemService(Context.WINDOW_SERVICE) as WindowManager

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var instance: GlobalMessageManager? = null

        fun getInstance(context: Context): GlobalMessageManager {
            if (instance == null) {
                instance = GlobalMessageManager(context.applicationContext)
            }
            return instance!!
        }
    }

    @SuppressLint("MissingInflatedId")
    fun showMsg(
        msg: String = "",
        msgType: Int = 0,
        rootView: ViewGroup? = null,
        backgroundRes: Int = R.drawable.shape_snackbar
    ) {
        val context = contextRef.get() ?: return
        if (!Settings.canDrawOverlays(context)) {
            // 没有权限，可能需要引导用户开启权限
            return
        }

        Handler(Looper.getMainLooper()).post {
            // 移除当前显示的View（如果有）
            currentView?.takeIf { isViewAttached }?.let {
                wm.removeViewImmediate(it)
                isViewAttached = false
                Log.e("移除", "移除")
            } ?: run {
                Log.e("空", "空")
            }

            val params = WindowManager.LayoutParams().apply {
                type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                } else {
                    @Suppress("DEPRECATION")
                    WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
                }
                format = PixelFormat.TRANSLUCENT
                flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                width = WindowManager.LayoutParams.WRAP_CONTENT
                height = WindowManager.LayoutParams.WRAP_CONTENT
                gravity = Gravity.BOTTOM
                val bottomMarginInPixels = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    16f,
                    Resources.getSystem().displayMetrics
                ).toInt()
                y = bottomMarginInPixels
            }

            val layout =
                LayoutInflater.from(context).inflate(R.layout.snackbar_layout, rootView, false)
                    .apply {
                        findViewById<TextView>(R.id.snackbar_text).text = msg
                        val icon = when (msgType) {
                            0 -> R.drawable.vector_notice
                            1 -> R.drawable.baseline_check_circle_24
                            else -> R.drawable.vector_error
                        }
                        findViewById<ImageView>(R.id.snackbar_icon).setImageResource(icon)
                        findViewById<FrameLayout>(R.id.snackBar).background =
                            ContextCompat.getDrawable(context, backgroundRes)
                    }

            // 更新当前显示的View
            currentView = layout
            wm.addView(layout, params)
            isViewAttached = true
            // 设置定时器，移除View
            Handler(Looper.getMainLooper()).postDelayed({
                if (layout == currentView && isViewAttached) {
                    wm.removeView(layout)
                    isViewAttached = false
                    currentView = null
                }
            }, 3000)
        }
    }
}
