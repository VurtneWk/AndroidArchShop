package com.vurtnewk.hi.ui.tab.top

import android.content.Context
import android.graphics.Color
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import com.vurtnewk.hi.ui.R
import com.vurtnewk.hi.ui.tab.common.IHiTab


/**
 * author:      vurtnewk
 *
 * description:
 */
class HiTabTop @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : RelativeLayout(context, attrs), IHiTab<HiTabTopInfo<*>> {
    lateinit var tabInfo: HiTabTopInfo<*>
    private val tabImageView: ImageView
    private val tabNameView: TextView
    private val indicator: View

    init {
        LayoutInflater.from(context).inflate(R.layout.hi_tab_top, this)
        tabImageView = findViewById(R.id.iv_image)
        tabNameView = findViewById(R.id.tv_name)
        indicator = findViewById(R.id.tab_top_indicator)
    }


    override fun setHiTabInfo(data: HiTabTopInfo<*>) {
        tabInfo = data
        inflateInfo(selected = false, init = true)
    }

    private fun inflateInfo(selected: Boolean, init: Boolean) {
        if (!::tabInfo.isInitialized) {
            throw RuntimeException("must call setHiTabInfo")
        }
        when (tabInfo.tabType) {
            HiTabTopInfo.TabType.BITMAP -> {
                if (init) {
                    tabImageView.visibility = VISIBLE
                    tabNameView.visibility = GONE
                    if (!TextUtils.isEmpty(tabInfo.name)) {
                        tabNameView.text = tabInfo.name
                    }
                }
                if (selected) {
                    indicator.visibility = VISIBLE
                    tabImageView.setImageBitmap(tabInfo.selectedBitmap)
                } else {
                    indicator.visibility = GONE
                    tabImageView.setImageBitmap(tabInfo.defaultBitmap)
                }
            }

            HiTabTopInfo.TabType.TXT -> {
                if (init) {
                    tabImageView.visibility = GONE
                    tabNameView.visibility = VISIBLE
                    if (tabInfo.name.isNotBlank()) {
                        tabNameView.text = tabInfo.name
                    }
                }
                if (selected) {
                    indicator.visibility = VISIBLE
                    tabNameView.setTextColor(getTextColor(tabInfo.tintColor!!))
                } else {
                    indicator.visibility = GONE
                    tabNameView.setTextColor(getTextColor(tabInfo.defaultColor!!))
                }
            }
        }
    }

    override fun resetHeight(height: Int) {
        val lp = layoutParams
        lp.height = height
        layoutParams = lp
        tabNameView.visibility = GONE
    }

    override fun onTabSelectedChange(index: Int, prevInfo: HiTabTopInfo<*>?, nextInfo: HiTabTopInfo<*>) {
        if (prevInfo != tabInfo && nextInfo != tabInfo || prevInfo == nextInfo) {
            return
        }
        inflateInfo(prevInfo != tabInfo, false)
    }


    @ColorInt
    fun getTextColor(color: Any): Int {
        return if (color is String) {
            Color.parseColor(color)
        } else {
            color as Int
        }
    }
}