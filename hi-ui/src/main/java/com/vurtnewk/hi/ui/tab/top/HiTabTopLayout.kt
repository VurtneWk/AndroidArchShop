package com.vurtnewk.hi.ui.tab.top

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import com.vurtnewk.hi.library.log.HiLogManager.Companion.init
import com.vurtnewk.hi.library.utils.getDisplayWidthPx
import com.vurtnewk.hi.ui.tab.common.IHiTabLayout
import kotlin.math.abs

/**
 * author:      vurtnewk
 *
 * description:
 */
class HiTabTopLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : HorizontalScrollView(context, attrs), IHiTabLayout<HiTabTop, HiTabTopInfo<*>> {

    private val infoList: MutableList<HiTabTopInfo<*>> = mutableListOf()
    private val tabSelectedChangeListeners =
        mutableListOf<IHiTabLayout.OnTabSelectedListener<HiTabTopInfo<*>>>()
    private var selectedInfo: HiTabTopInfo<*>? = null
    private var tabWidth = 0

    init {
        isHorizontalScrollBarEnabled = false
    }


    override fun findTab(data: HiTabTopInfo<*>): HiTabTop? {
        val rootView = getRootLayout(false)
        for (i in 0 until rootView.childCount) {
            val child: View = rootView.getChildAt(i)
            if (child is HiTabTop) {
                if (child.tabInfo === data) {
                    return child
                }
            }
        }
        return null
    }

    override fun addTabSelectedChangeListener(listener: IHiTabLayout.OnTabSelectedListener<HiTabTopInfo<*>>) {
        tabSelectedChangeListeners.add(listener)
    }

    override fun defaultSelected(defaultInfo: HiTabTopInfo<*>) {
        onSelected(defaultInfo)
    }

    override fun inflateInfo(infoList: List<HiTabTopInfo<*>>) {
        if (infoList.isEmpty()) return
        this.infoList.clear()
        this.infoList.addAll(infoList)
        //移除之前已经添加的 View
        // index = 0 是,中间的内容部分
        for (index in childCount - 1 downTo 1) {
            removeViewAt(index)
        }
        selectedInfo = null
        val linearLayout = getRootLayout(true)

        val iterator = tabSelectedChangeListeners.iterator()
        while (iterator.hasNext()) {
            if (iterator.next() is HiTabTop) {
                iterator.remove()
            }
        }
        for (index in infoList.indices) {
            val hiTabTopInfo = infoList[index]

            val hiTabTop = HiTabTop(context)
            tabSelectedChangeListeners.add(hiTabTop)
            hiTabTop.setHiTabInfo(hiTabTopInfo)
            hiTabTop.setOnClickListener { onSelected(hiTabTopInfo) }

            linearLayout.addView(hiTabTop)
        }
    }

    private fun onSelected(hiTabTopInfo: HiTabTopInfo<*>) {
        for (listener in tabSelectedChangeListeners) {
            listener.onTabSelectedChange(infoList.indexOf(hiTabTopInfo), selectedInfo, hiTabTopInfo)
        }
        this.selectedInfo = hiTabTopInfo
        autoScroll(hiTabTopInfo)
    }

    /**
     * 自动滚动
     */
    private fun autoScroll(hiTabTopInfo: HiTabTopInfo<*>) {
        val hiTabTop = findTab(hiTabTopInfo) ?: return
        val index = infoList.indexOf(hiTabTopInfo)
        //获取点击的控件在屏幕的位置
        val location = IntArray(2)
        hiTabTop.getLocationInWindow(location)
        if (tabWidth == 0) {
            tabWidth = hiTabTop.width
        }
        //判断点击了屏幕左侧还是右侧
        val scrollWidth = if (location[0] + tabWidth / 2 > getDisplayWidthPx() / 2) { // 右侧
            rangeScrollWidth(index, 2)
        } else {
            rangeScrollWidth(index, -2)
        }
        scrollTo(scrollX + scrollWidth, 0)
    }

    /**
     * 获取可滚动的范围
     * @param index 从第几个开始
     * @param range 向前向后的范围 向后是正数/向前是负数
     * @return 可滚动的范围
     */
    private fun rangeScrollWidth(index: Int, range: Int): Int {
        var scrollWidth = 0
        for (i in 0..abs(range)) {
            // TODO
            val next = if (range < 0) {
                range + i + index
            } else {
                range - i + index
            }
            if (next >= 0 && next < infoList.size) {
                if (range < 0) {
                    scrollWidth -= scrollWidth(next, false)
                } else {
                    scrollWidth += scrollWidth(next, true)
                }
            }
        }
        return scrollWidth
    }

    /**
     * 指定位置的控件可滚动的距离
     * @param index   指定位置的控件
     * @param toRight 是否是点击了屏幕右侧
     * @return 可滚动的距离
     */
    private fun scrollWidth(index: Int, toRight: Boolean): Int {
        val target = findTab(infoList[index]) ?: return 0
        // 获取 target 在屏幕的课件区域
        val rect = Rect()
        target.getLocalVisibleRect(rect)
        if (toRight) {
            // right坐标大于控件的宽度时，说明完全没有显示 ?? 这里注释好像是有问题的
            return if (rect.right > tabWidth) { // ?? 这里的 rect.right > tabWidth 应该是永远都进不去
                tabWidth
            } else { //显示部分，减去已显示的宽度
                tabWidth - rect.right
            }
        } else {
            //left坐标小于等于-控件的宽度，说明完全没有显示
            return if (rect.left <= -tabWidth) {
                tabWidth
            } else if (rect.left > 0) {
                //显示部分
                rect.left
            } else {
                0
            }
        }
    }


    /**
     * 创建外层的 线性布局
     */
    private fun getRootLayout(clear: Boolean): LinearLayout {
        var rootView = getChildAt(0) as? LinearLayout
        if (rootView == null) {
            rootView = LinearLayout(context)
            rootView.orientation = LinearLayout.HORIZONTAL
            val layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
            addView(rootView, layoutParams)
        } else if (clear) {
            rootView.removeAllViews()
        }
        return rootView
    }

}