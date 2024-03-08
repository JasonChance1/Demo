package com.example.demo.util

import android.content.Context
import android.content.res.Resources
import android.graphics.PixelFormat
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.transition.TransitionManager
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.demo.R
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException

/**
 * @description:
 * @author yanglei
 * @date :2024/3/4
 * @version 1.0.0
 */
fun String?.toJson(): JsonObject? {
    if (this.isNullOrEmpty()) return null
    return try {
        // 尝试将字符串解析为JsonObject
        JsonParser.parseString(this).asJsonObject ?: Gson().fromJson(
            this.format(),
            JsonObject::class.java
        ) ?: null
    } catch (e: JsonSyntaxException) {
        // 如果字符串不是有效的JSON格式，尝试其他规范化逻辑或直接返回null
        null
    } catch (e: IllegalStateException) {
        // 如果不是JsonObject类型，也返回null
        null
    }
}

/**
 * 控制视图是否可见
 * @param isVisible true-显示 false-隐藏
 */
fun View.setVisible(isVisible: Boolean, showAnim: Boolean = false) {
    if (showAnim) {
        TransitionManager.beginDelayedTransition(this.rootView as? ViewGroup)
    }
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

/**
 * 展开、收起箭头旋转动画
 * @param isVisible 内容是否可见（展开-true/收起-false）
 */
fun View.addRotateAnimate(isVisible: Boolean) {
    val rotate = if (isVisible) 180f else 0f
    animate().rotation(rotate).setDuration(250).start()
}


/**
 * 内容设置为可展开收起
 * view为标题
 * @param contentView 需要展开收起的视图
 */
fun View.setExpandable(
    contentView: View,
    arrow: View? = null
) {
    setOnClickListener {
        val isVisible = contentView.visibility == View.VISIBLE
        TransitionManager.beginDelayedTransition(this.rootView as? ViewGroup)
        contentView.visibility = if (isVisible) View.GONE else View.VISIBLE
        arrow?.addRotateAnimate(isVisible)
    }
}

fun Context.showError(msg: String) = showMsg(msg, 2)
fun Context.showSuccess(msg: String) = showMsg(msg, 1)
fun Context.showNotice(msg: String) = showMsg(msg, 0)
fun Context.showMsg(msg: String = "", msgType: Int = 0,rootView:ViewGroup? = null,backgroundRes:Int? = null) {
    if (!Settings.canDrawOverlays(this)) {
        // 没有权限，可能需要引导用户开启权限
        Log.e("无权限", "无权限")
        return
    }
    val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
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
        // 设置底部边距，假设我们想要的边距是50dp
        val bottomMarginInPixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16f, Resources.getSystem().displayMetrics).toInt()
        y = bottomMarginInPixels // 使用正值向上移动，产生底部边距的效果
    }

    val inflater = LayoutInflater.from(this)
    val layout = inflater.inflate(R.layout.snackbar_layout, rootView)

    layout.findViewById<TextView>(R.id.snackbar_text).text = msg
    val icon = when (msgType) {
        0 -> R.drawable.vector_notice
        1 -> R.drawable.baseline_check_circle_24
        else -> R.drawable.vector_error
    }
    layout.findViewById<ImageView>(R.id.snackbar_icon).apply {
        setImageDrawable(ContextCompat.getDrawable(layout.context, icon))
    }
    backgroundRes?.let {
        layout.background =
            ContextCompat.getDrawable(layout.context, it)
    }
    // 添加视图到窗口
    wm.addView(layout, params)

    Handler(Looper.getMainLooper()).postDelayed({
        wm.removeView(layout)
    }, 3000) // 3秒后自动移除
}