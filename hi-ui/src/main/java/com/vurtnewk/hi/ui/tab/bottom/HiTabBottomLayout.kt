package com.vurtnewk.hi.ui.tab.bottom

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.FrameLayout
import android.widget.ScrollView
import androidx.recyclerview.widget.RecyclerView
import com.vurtnewk.hi.library.utils.dp2px
import com.vurtnewk.hi.library.utils.findTypeView
import com.vurtnewk.hi.library.utils.getDisplayWidthPx
import com.vurtnewk.hi.ui.R
import com.vurtnewk.hi.ui.tab.common.IHiTabLayout


/**
 * author:      vurtnewk
 *
 * description:
 */
class HiTabBottomLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs), IHiTabLayout<HiTabBottom, HiTabBottomInfo<*>> {

    companion object {
        private const val TAG_TAB_BOTTOM = "TAG_TAB_BOTTOM"
    }

    private val tabSelectedChangeListeners =
        mutableListOf<IHiTabLayout.OnTabSelectedListener<HiTabBottomInfo<*>>>()
    private var selectedInfo: HiTabBottomInfo<*>? = null
    var bottomAlpha = 1f

    //TabBottom高度
    var tabBottomHeight: Float = 50f

    //TabBottom的头部线条高度
    private val bottomLineHeight = 0.5f

    //TabBottom的头部线条颜色
    var bottomLineColor = "#DFE0E1"
    private val infoList: MutableList<HiTabBottomInfo<*>> = mutableListOf()


    override fun findTab(data: HiTabBottomInfo<*>): HiTabBottom? {
        val frameLayout = findViewWithTag<FrameLayout>(TAG_TAB_BOTTOM)
        for (i in 0 until frameLayout.childCount) {
            val child: View = frameLayout.getChildAt(i)
            if (child is HiTabBottom) {
                if (child.tabInfo === data) {
                    return child
                }
            }
        }
        return null
    }

    override fun addTabSelectedChangeListener(listener: IHiTabLayout.OnTabSelectedListener<HiTabBottomInfo<*>>) {
        tabSelectedChangeListeners.add(listener)
    }

    override fun defaultSelected(defaultInfo: HiTabBottomInfo<*>) {
        onSelected(defaultInfo)
    }

    override fun inflateInfo(infoList: List<HiTabBottomInfo<*>>) {
        if (infoList.isEmpty()) return
        this.infoList.clear()
        this.infoList.addAll(infoList)
        //移除之前已经添加的 View
        // index = 0 是,中间的内容部分
        for (index in childCount - 1 downTo 1) {
            removeViewAt(index)
        }
        selectedInfo = null
        addBackground() // TODO( 为什么不直接 this.setBackgroundColor()?)

        val iterator = tabSelectedChangeListeners.iterator()
        while (iterator.hasNext()) {
            if (iterator.next() is HiTabBottom) {
                iterator.remove()
            }
        }
        // 包裹 HiTabBottom 的布局
        val frameLayout = FrameLayout(context)
        frameLayout.tag = TAG_TAB_BOTTOM

        val width = getDisplayWidthPx() / infoList.size
        val height = dp2px(tabBottomHeight)

        for (index in infoList.indices) {
            val hiTabBottomInfo = infoList[index]
            val layoutParams = LayoutParams(width, height)
            layoutParams.gravity = Gravity.BOTTOM
            layoutParams.leftMargin = index * width

            val hiTabBottom = HiTabBottom(context)
            tabSelectedChangeListeners.add(hiTabBottom)
            hiTabBottom.setHiTabInfo(hiTabBottomInfo)
            hiTabBottom.setOnClickListener { onSelected(hiTabBottomInfo) }
            frameLayout.addView(hiTabBottom, layoutParams)
        }

        val layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        layoutParams.gravity = Gravity.BOTTOM
        addBottomLine()
        addView(frameLayout, layoutParams)
        fixContentView()
    }

    private fun fixContentView() {
        if (getChildAt(0) !is ViewGroup) return
        val rootView = getChildAt(0) as ViewGroup
        var targetView: ViewGroup? = findTypeView(rootView, RecyclerView::class.java)
        if (targetView == null) {
            targetView = findTypeView(rootView, ScrollView::class.java)
        }
        if (targetView == null) {
            targetView = findTypeView(rootView, AbsListView::class.java)
        }
        targetView?.setPadding(0, 0, 0, dp2px(tabBottomHeight))
        targetView?.clipToPadding = false
    }


    private fun onSelected(nextInfo: HiTabBottomInfo<*>) {
        for (listener in tabSelectedChangeListeners) {
            listener.onTabSelectedChange(infoList.indexOf(nextInfo), selectedInfo, nextInfo)
        }
        this.selectedInfo = nextInfo
    }

    private fun addBottomLine() {
        val bottomLine = View(context)
        bottomLine.setBackgroundColor(Color.parseColor(bottomLineColor))
        val bottomLineParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp2px(bottomLineHeight))
        bottomLineParams.gravity = Gravity.BOTTOM
        bottomLineParams.bottomMargin = dp2px(tabBottomHeight - bottomLineHeight)
        addView(bottomLine, bottomLineParams)
        bottomLine.alpha = bottomAlpha
    }

    /**
     * 添加背景
     */
    @SuppressLint("InflateParams")
    private fun addBackground() {
        val view: View = LayoutInflater.from(context).inflate(R.layout.hi_bottom_layout_bg, null)
        val params = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp2px(tabBottomHeight))
        params.gravity = Gravity.BOTTOM
        addView(view, params)
        view.alpha = bottomAlpha
    }
}