package com.example.demo.util

import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
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
    if (showAnim){
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