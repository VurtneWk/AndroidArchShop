package com.vurtnewk.hi.library.log.printer.view

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vurtnewk.hi.library.utils.dp2px

/**
 * author:      vurtnewk
 *
 * description:
 */
class HiViewPrinterProvider(private val rootView: FrameLayout, private val recyclerView: RecyclerView) {

    private var floatingView: View? = null
    private var logView: FrameLayout? = null
    private var isOpen = false

    companion object {
        const val TAG_FLOATING_VIEW: String = "TAG_FLOATING_VIEW"
        const val TAG_LOG_VIEW: String = "TAG_LOG_VIEW"
    }

    /**
     * 显示悬浮按钮
     */
    fun showFloatingView() {
        // 已经添加过
        if (rootView.findViewWithTag<View>(TAG_FLOATING_VIEW) != null) return
        val params = FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        params.gravity = Gravity.BOTTOM or Gravity.END
        createFloatingView()
        with(floatingView!!) {
            tag = TAG_FLOATING_VIEW
            setBackgroundColor(Color.BLACK)
            alpha = 0.8F
        }
        params.bottomMargin = dp2px(100F)
        rootView.addView(floatingView, params)
    }

    /**
     * 显示LogView
     */
    private fun showLogView() {
        if (rootView.findViewWithTag<View>(TAG_LOG_VIEW) != null) return
        val params = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp2px(160F))
        params.gravity = Gravity.BOTTOM
        createLogView()
        logView!!.tag = TAG_LOG_VIEW
        rootView.addView(logView, params)
        isOpen = true
    }

    private fun closeLogView() {
        isOpen = false
        rootView.removeView(logView)
    }

    /**
     * 创建悬浮按钮
     */
    @SuppressLint("SetTextI18n")
    private fun createFloatingView(): View {
        if (floatingView != null) {
            return floatingView!!
        }
        val textView = TextView(rootView.context)
        textView.setOnClickListener {
            if (!isOpen) {
                showLogView()
            }
        }
        textView.text = "HiLog"
        floatingView = textView
        return floatingView!!
    }

    @SuppressLint("SetTextI18n")
    private fun createLogView(): View {
        if (logView != null) return logView!!
        logView = FrameLayout(rootView.context)
        logView!!.setBackgroundColor(Color.BLACK)
        logView!!.addView(recyclerView)
        val params = FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        params.gravity = Gravity.END
        // 创建关闭按钮
        val closeView = TextView(rootView.context)
        closeView.setOnClickListener { closeLogView() }
        closeView.text = "Close"
        logView!!.addView(closeView, params)
        return logView!!
    }
}