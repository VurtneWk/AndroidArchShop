package com.vurtnewk.hi.ui.banner.core

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager
import kotlinx.coroutines.NonCancellable.start

/**
 * author:      vurtnewk
 *
 * 实现自动翻页的 ViewPager
 */
@Suppress("MemberVisibilityCanBePrivate")
class HiViewPager(context: Context, attrs: AttributeSet?) : ViewPager(context, attrs) {

    /**
     * 时间间隔 毫秒
     */
    var intervalTime = 0L

    private val mHandler = Handler(Looper.getMainLooper())
    private val mRunnable = object : Runnable {
        override fun run() {
            next()
            mHandler.postDelayed(this, intervalTime)
        }
    }

    /**
     * 是否自动播放, 默认为true 自动播放
     */
    var autoPlay = true
        set(value) {
            field = value
            // 如果设置成不支持自动播放,移除之前的Runnable
            if (!value) {
                mHandler.removeCallbacks(mRunnable)
            }
        }

    private var isLayout = false

    /**
     * 用户触摸时,关闭自动播放 , 触摸结束后 开启自动播放
     */
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> start()
            else -> stop()
        }
        return super.onTouchEvent(ev)
    }

    /**
     * ViewPager 类 使用的 Scroller 类变量 mScroller 来控制滚动的
     *
     * Scroller 类 使用的 startScroll 方法来控制速度
     *
     * 所以使用反射 把ViewPager 类的 Scroller 替换为 HiBannerScroller
     */
    fun setScrollDuration(duration: Int) {
        kotlin.runCatching {
            val mScrollerField = ViewPager::class.java.getDeclaredField("mScroller")
            mScrollerField.isAccessible = true
            mScrollerField.set(this, HiBannerScroller(context, duration))
        }
    }

    fun start() {
        mHandler.removeCallbacksAndMessages(null)
        if (autoPlay) {
            mHandler.postDelayed(mRunnable, intervalTime)
        }
    }

    fun stop() {
        mHandler.removeCallbacksAndMessages(null)
    }

    /**
     * 设置下一个要显示的item，并返回item的pos
     *
     */
    private fun next(): Int {
        var nextPosition = -1
        if (adapter == null || adapter!!.count <= 1) {
            stop()
            return nextPosition
        }
        nextPosition = currentItem + 1
        //下一个索引大于adapter的view的最大数量时重新开始
        if (nextPosition >= adapter!!.count) {
            // 获取第一个 item 的索引
            nextPosition = (adapter as HiBannerAdapter).getFirstItemPosition()
        }
        setCurrentItem(nextPosition, true)
        return nextPosition
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        isLayout = true
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        //https://blog.csdn.net/u011002668/article/details/72884893
        try {
            if (isLayout && adapter != null && adapter!!.count > 0) {
                val mFirstLayoutField = ViewPager::class.java.getDeclaredField("mFirstLayout")
                mFirstLayoutField.isAccessible = true
                mFirstLayoutField.set(this, false)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        start()
    }

    override fun onDetachedFromWindow() {
        if (context is Activity && (context as Activity).isFinishing) {
            super.onDetachedFromWindow()
        }
        stop()
    }
}