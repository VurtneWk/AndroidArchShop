package com.vurtnewk.hi.ui.tab.bottom

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
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
class HiTabBottom @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : RelativeLayout(context, attrs), IHiTab<HiTabBottomInfo<*>> {
    lateinit var tabInfo: HiTabBottomInfo<*>
    private val tabImageView: ImageView
    private val tabIconView: TextView
    private val tabNameView: TextView

    init {
        LayoutInflater.from(context).inflate(R.layout.hi_tab_bottom, this)
        tabImageView = findViewById(R.id.iv_image)
        tabIconView = findViewById(R.id.tv_icon)
        tabNameView = findViewById(R.id.tv_name)
    }


    override fun setHiTabInfo(data: HiTabBottomInfo<*>) {
        tabInfo = data
        inflateInfo(selected = false, init = true)
    }

    private fun inflateInfo(selected: Boolean, init: Boolean) {
        if (!::tabInfo.isInitialized) {
            throw RuntimeException("must call setHiTabInfo")
        }
        when (tabInfo.tabType) {
            HiTabBottomInfo.TabType.BITMAP -> {
                if (init) {
                    tabImageView.visibility = VISIBLE
                    tabIconView.visibility = GONE
                    if (!TextUtils.isEmpty(tabInfo.name)) {
                        tabNameView.text = tabInfo.name
                    }
                }
                if (selected) {
                    tabImageView.setImageBitmap(tabInfo.selectedBitmap)
                } else {
                    tabImageView.setImageBitmap(tabInfo.defaultBitmap)
                }
            }

            HiTabBottomInfo.TabType.ICON -> {
                if (init) {
                    tabImageView.visibility = GONE
                    tabIconView.visibility = VISIBLE
                    val typeface = Typeface.createFromAsset(context.assets, tabInfo.iconFont)
                    tabIconView.setTypeface(typeface)
                    if (tabInfo.name.isNotBlank()) {
                        tabNameView.text = tabInfo.name
                    }
                }
                if (selected) {
                    tabIconView.text =
                        if (TextUtils.isEmpty(tabInfo.selectedIconName)) tabInfo.defaultIconName else tabInfo.selectedIconName
                    tabIconView.setTextColor(getTextColor(tabInfo.tintColor!!))
                    tabNameView.setTextColor(getTextColor(tabInfo.tintColor!!))
                } else {
                    tabIconView.text = tabInfo.defaultIconName
                    tabInfo.defaultColor?.let {
                        tabIconView.setTextColor(getTextColor(it))
                        tabNameView.setTextColor(getTextColor(it))
                    }
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

    override fun onTabSelectedChange(index: Int, prevInfo: HiTabBottomInfo<*>?, nextInfo: HiTabBottomInfo<*>) {
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