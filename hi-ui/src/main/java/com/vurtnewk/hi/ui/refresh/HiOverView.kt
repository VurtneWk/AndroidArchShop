package com.vurtnewk.hi.ui.refresh

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.vurtnewk.hi.library.utils.dp2px

/**
 * author:      vurtnewk
 *
 * 顶部刷新视图
 */
abstract class HiOverView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    /**
     * 刷新时的状态
     */
    enum class HiRefreshState {
        /**
         * 初始态
         */
        STATE_INIT,

        /**
         * Header展示的状态
         */
        STATE_VISIBLE,

        /**
         * 超出可刷新距离的状态
         */
        STATE_OVER,

        /**
         * 刷新中的状态
         */
        STATE_REFRESH,

        /**
         * 超出刷新位置松开手后的状态
         */
        STATE_OVER_RELEASE
    }


    var mState = HiRefreshState.STATE_INIT

    /**
     * 触发下拉刷新 需要的最小高度
     */
    var mPullRefreshHeight = dp2px(66F)

    /**
     * 最小阻尼
     */
    var minDamp = 1.6F

    /**
     * 最大阻尼
     */
    var maxDamp = 2.2F


    init {
        this.init()
    }

    /**
     * 初始化
     */
    abstract fun init()

    abstract fun onScroll(scrollY: Int, pullRefreshHeight: Int)

    /**
     * 显示Overlay
     */
    abstract fun onVisible()

    /**
     * 超过Overlay，释放就会加载
     */
    abstract fun onOver()

    /**
     * 开始加载
     */
    abstract fun onRefresh()

    /**
     * 加载完成
     */
    abstract fun onFinish()
}