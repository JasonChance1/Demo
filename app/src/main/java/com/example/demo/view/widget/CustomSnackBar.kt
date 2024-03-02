package com.example.demo.view.widget

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.demo.R
import com.google.android.material.snackbar.Snackbar
import java.lang.ref.WeakReference

/**
 * @param textMessage 提示内容
 * @param type 类型，不同类型的提示图标不同，normal-普通提示，error-错误提示，complete-成功的提示
 * @param backgroundColorRes 提示的背景资源，可以传入自定义的背景样式
 */
class CustomSnackBar(
    private val context: Context,
    private var msg: String,
    private val iconRes: Int = R.drawable.vector_notice,
    private val attachView: View? = null,
    private val backgroundColorRes: Int? = null,
) {
    // 使用 context 的弱引用，避免内存泄漏
    private val contextRef = WeakReference(context)

    @SuppressLint("InflateParams")
    private val snackbarLayout: View =
        LayoutInflater.from(context).inflate(R.layout.snackbar_layout, null)

    private val snackbar: Snackbar by lazy {
        val rootView: View? = getActivityContentView()
        Snackbar.make(rootView ?: snackbarLayout, "", Snackbar.LENGTH_LONG).apply {
            val layout = this.view as Snackbar.SnackbarLayout
            layout.setPadding(0, 0, 0, 0)
            layout.addView(snackbarLayout, 0)
            contextRef.get()?.let {
                if (it is Activity) {
                    layout.setBackgroundColor(ContextCompat.getColor(it, android.R.color.transparent))
                }
            }
        }
    }

    init {
        setupSnackbar()
    }

    private fun getActivityContentView(): View? {
        val context = contextRef.get()
        return if (context is Activity) {
            context.findViewById(android.R.id.content)
        } else null
    }

    private fun setupSnackbar() {
        snackbarLayout.findViewById<TextView>(R.id.snackbar_text).text = msg

        val layoutParams = snackbarLayout.layoutParams as? FrameLayout.LayoutParams
            ?: FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            )

        layoutParams.width = FrameLayout.LayoutParams.WRAP_CONTENT
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL
        snackbarLayout.layoutParams = layoutParams

        snackbarLayout.findViewById<ImageView>(R.id.snackbar_icon).apply {
            setImageDrawable(ContextCompat.getDrawable(snackbarLayout.context, iconRes))
        }

        backgroundColorRes?.let {
            snackbarLayout.background =
                ContextCompat.getDrawable(snackbarLayout.context, it)
        }
        attachView?.let {
            snackbar.anchorView = it
        }

    }

    private fun show() {
        snackbar.show()
    }

    fun show(attachView: View? = null, backgroundColorRes: Int? = null) {
        CustomSnackBar(
            context,
            msg,
            R.drawable.vector_notice,
            attachView,
            backgroundColorRes
        ).show()
    }

    fun showError(attachView: View? = null, backgroundColorRes: Int? = null) {
        CustomSnackBar(context, msg, R.drawable.vector_error, attachView, backgroundColorRes).show()
    }

    fun showSuccess(attachView: View? = null, backgroundColorRes: Int? = null) {
        CustomSnackBar(
            context,
            msg,
            R.drawable.baseline_check_circle_24,
            attachView,
            backgroundColorRes
        ).show()
    }
}
