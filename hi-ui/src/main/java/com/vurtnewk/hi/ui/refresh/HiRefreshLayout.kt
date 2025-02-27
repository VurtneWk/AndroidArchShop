package com.vurtnewk.hi.ui.refresh

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import android.widget.Scroller
import com.vurtnewk.hi.library.log.HiLog.i
import com.vurtnewk.hi.ui.refresh.HiOverView.HiRefreshState
import kotlin.math.abs


/**
 * author:      vurtnewk
 *
 * 最外层的容器
 */
class HiRefreshLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs), HiRefresh {

    private var mState = HiRefreshState.STATE_INIT
    private val mGestureDetector: GestureDetector
    var mHiRefreshListener: HiRefresh.HiRefreshListener? = null
    private var mDisableRefreshScroll: Boolean = false  //刷新时是否禁止滚动
    var mHiOverView: HiOverView? = null
        set(value) {
            if (field != null) {
                removeView(field)
            }
            if (value == null) return
            field = value
            val params = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            addView(field, 0, params)
        }
    private val mAutoScroller = AutoScroller()
    private var mLastY = 0

    private val mHiGestureDetector = object : HiGestureDetector() {
        override fun onScroll(e1: MotionEvent?, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
            if (abs(distanceX) > abs(distanceY) || (mHiRefreshListener != null && !mHiRefreshListener!!.enableRefresh())) {
                return false  //横向滑动，或刷新被禁止则不处理
            }
            if (mDisableRefreshScroll && mState == HiRefreshState.STATE_REFRESH) {//刷新时是否禁止滑动
                return true
            }

            val child = HiScrollUtil.findScrollableChild(this@HiRefreshLayout)
            if (HiScrollUtil.scrolled(child)) {
                //如果列表发生了滚动则不处理
                return false
            }
            val headView = getChildAt(0)
            //没有刷新或没有达到可以刷新的距离，且头部已经划出或下拉
            if ((mState != HiRefreshState.STATE_REFRESH || headView.bottom <= mHiOverView!!.mPullRefreshHeight) && (headView.bottom > 0 || distanceX <= 0F)) {
                if (mState != HiRefreshState.STATE_OVER_RELEASE) {
                    //阻尼计算
                    val speed = if (child.top < mHiOverView!!.mPullRefreshHeight) {
                        (mLastY / mHiOverView!!.minDamp).toInt()
                    } else {
                        (mLastY / mHiOverView!!.maxDamp).toInt()
                    }
                    //如果是正在刷新状态，则不允许在滑动的时候改变状态
                    val bool = moveDown(speed, true)
                    mLastY = (-distanceY).toInt()
                    return bool
                } else {
                    return false
                }
            } else {
                return false
            }
        }
    }

    init {
        mGestureDetector = GestureDetector(context, mHiGestureDetector)
    }

    override fun setDisableRefreshScroll(disableRefreshScroll: Boolean) {
        this.mDisableRefreshScroll = disableRefreshScroll
    }

    override fun refreshFinished() {
        val head = getChildAt(0)
        mHiOverView!!.onFinish()
        mHiOverView!!.mState = HiRefreshState.STATE_INIT
        if (head.bottom > 0) {
            recover(head.bottom)
        }
        mState = HiRefreshState.STATE_INIT
    }

    override fun setRefreshListener(hiRefreshListener: HiRefresh.HiRefreshListener) {
        this.mHiRefreshListener = hiRefreshListener
    }

    override fun setRefreshOverView(hiOverView: HiOverView) {
        if (this.mHiOverView != null) {
            removeView(mHiOverView)
        }
        mHiOverView = hiOverView
        val params = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        addView(mHiOverView, 0, params)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        //定义head和child的排列位置
        val head = getChildAt(0)
        val child = getChildAt(1)
        if (head != null && child != null) {
            val childTop = child.top
            if (mState == HiRefreshState.STATE_REFRESH) {
                head.layout(
                    0, mHiOverView!!.mPullRefreshHeight - head.measuredHeight, right, mHiOverView!!.mPullRefreshHeight
                )
                child.layout(
                    0, mHiOverView!!.mPullRefreshHeight, right, mHiOverView!!.mPullRefreshHeight + child.measuredHeight
                )
            } else {
                //left,top,right,bottom
                head.layout(0, childTop - head.measuredHeight, right, childTop)
                child.layout(0, childTop, right, childTop + child.measuredHeight)
            }

            var other: View
            for (i in 2 until childCount) {
                other = getChildAt(i)
                other.layout(0, top, right, bottom)
            }
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val head = getChildAt(0) ?: return super.dispatchTouchEvent(ev)
        if (ev.action == MotionEvent.ACTION_UP || ev.action == MotionEvent.ACTION_CANCEL) {
            if (head.bottom > 0) { // 说明head被拉下来了
                // 如果不是正在刷新
                if (mState != HiRefreshState.STATE_REFRESH) {
                    recover(head.bottom)
                    return false
                }
            }
            mLastY = 0
        }
        //
        val consumed = mGestureDetector.onTouchEvent(ev)
        if ((consumed || (mState != HiRefreshState.STATE_INIT && mState != HiRefreshState.STATE_REFRESH)) && head.bottom != 0) {
            ev.action = MotionEvent.ACTION_CANCEL
            return super.dispatchTouchEvent(ev)
        }

        if (consumed) return true
        return super.dispatchTouchEvent(ev)
    }

    private fun recover(distance: Int) {
        if (mHiRefreshListener != null && mHiOverView != null && distance > mHiOverView!!.mPullRefreshHeight) {
            // 滚动到指定为止
            mAutoScroller.recover(distance - mHiOverView!!.mPullRefreshHeight)
            mState = HiRefreshState.STATE_OVER_RELEASE
        } else {
            mAutoScroller.recover(distance)
        }
    }


    /**
     * 根据偏移量移动header与child
     *
     * @param offsetY 偏移量
     * @param nonAuto 是否非自动滚动触发
     * @return
     */
    private fun moveDown(offsetY: Int, nonAuto: Boolean): Boolean {
        var mOffsetY = offsetY
        val head = getChildAt(0)
        val child = getChildAt(1)
        val childTop = child.top + mOffsetY
        if (childTop <= 0) { //异常情况的补充
            mOffsetY = -child.top
            //移动head与child的位置，到原始位置
            head.offsetTopAndBottom(mOffsetY)
            child.offsetTopAndBottom(mOffsetY)
            if (mState != HiRefreshState.STATE_REFRESH) {
                mState = HiRefreshState.STATE_INIT
            }
        } else if (mState == HiRefreshState.STATE_REFRESH && childTop > mHiOverView!!.mPullRefreshHeight) {
            //如果正在下拉刷新中，禁止继续下拉
            return false
        } else if (childTop <= mHiOverView!!.mPullRefreshHeight) { //还没超出设定的刷新距离
            if (mHiOverView!!.mState !== HiRefreshState.STATE_VISIBLE && nonAuto) { //头部开始显示
                mHiOverView!!.onVisible()
                mHiOverView!!.mState = HiRefreshState.STATE_VISIBLE
                mState = HiRefreshState.STATE_VISIBLE
            }
            head.offsetTopAndBottom(mOffsetY)
            child.offsetTopAndBottom(mOffsetY)
            if (childTop == mHiOverView!!.mPullRefreshHeight && mState == HiRefreshState.STATE_OVER_RELEASE) {
                refresh()
            }
        } else {
            if (mHiOverView!!.mState !== HiRefreshState.STATE_OVER && nonAuto) {
                //超出刷新位置
                mHiOverView!!.onOver()
                mHiOverView!!.mState = HiRefreshState.STATE_OVER
            }
            head.offsetTopAndBottom(mOffsetY)
            child.offsetTopAndBottom(mOffsetY)
        }
        if (mHiOverView != null) {
            mHiOverView!!.onScroll(head.bottom, mHiOverView!!.mPullRefreshHeight)
        }
        return true
    }

    /**
     * 刷新
     */
    private fun refresh() {
        if (mHiRefreshListener != null) {
            mState = HiRefreshState.STATE_REFRESH
            mHiOverView!!.onRefresh()
            mHiOverView!!.mState = HiRefreshState.STATE_REFRESH
            mHiRefreshListener!!.onRefresh()
        }
    }

    /**
     *
     * *** 持有外部类引用 ***
     */
    private inner class AutoScroller : Runnable {

        var isFinished = true
            private set

        private val mScroller = Scroller(context, LinearInterpolator())
        private var mLastY = 0

        override fun run() {

            if (mScroller.computeScrollOffset()) {// 还未滚动完成
                moveDown(mLastY - mScroller.currY, false)
                mLastY = mScroller.currY // 最后一次滚动的位置
                post(this) // 再次调用
            } else {
                removeCallbacks(this) //移除任务
                isFinished = true
            }
        }


        fun recover(distance: Int) {
            if (distance <= 0) return
            removeCallbacks(this)
            mLastY = 0
            isFinished = false
            mScroller.startScroll(0, 0, 0, distance, 300)
            post(this)
        }
    }
}