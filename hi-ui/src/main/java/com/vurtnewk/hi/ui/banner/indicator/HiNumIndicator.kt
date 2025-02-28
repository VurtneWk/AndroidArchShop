package com.vurtnewk.hi.ui.banner.indicator

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.vurtnewk.hi.library.utils.dp2px

/**
 * author:      vurtnewk
 *
 * 数字指示器
 */
class HiNumIndicator @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs), HiIndicator<FrameLayout> {

    override fun get(): FrameLayout = this

    override fun onInflate(count: Int) {
        removeAllViews()
        if (count <= 0) return
        val groupView = LinearLayout(context)
        groupView.orientation = LinearLayout.HORIZONTAL
        groupView.setPadding(0, 0, dp2px(10F), dp2px(10F))

        groupView.addView(createText("1"))
        groupView.addView(createText("/"))
        groupView.addView(createText(count.toString()))

        val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        params.gravity = Gravity.END or Gravity.BOTTOM
        addView(groupView, params)
    }

    @SuppressLint("SetTextI18n")
    override fun onPointChange(current: Int, count: Int) {
        val viewGroup = getChildAt(0) as ViewGroup
        (viewGroup.getChildAt(0) as TextView).text = (current + 1).toString()
        (viewGroup.getChildAt(2) as TextView).text = count.toString()
    }

    private fun createText(text: String): TextView {
        val textView = TextView(context)
        textView.text = text
        textView.setTextColor(Color.WHITE)
        return textView
    }
}