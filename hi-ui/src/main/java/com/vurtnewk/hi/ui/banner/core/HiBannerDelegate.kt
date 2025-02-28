package com.vurtnewk.hi.ui.banner.core

import android.content.Context
import android.widget.FrameLayout
import androidx.viewpager.widget.ViewPager
import com.vurtnewk.hi.ui.R
import com.vurtnewk.hi.ui.banner.HiBanner
import com.vurtnewk.hi.ui.banner.indicator.HiCircleIndicator
import com.vurtnewk.hi.ui.banner.indicator.HiIndicator

/**
 * author:      vurtnewk
 *
 * HiBanner 的代理类, HiBanner 的 主要逻辑都在这个类 完成
 *
 * -------
 * 最好使用构建模式
 */
class HiBannerDelegate(val context: Context, val hiBanner: HiBanner) : ViewPager.OnPageChangeListener, IHiBanner {

    private var mHiBannerMos: List<HiBannerMo>? = null
    private var mAdapter: HiBannerAdapter? = null
    private var mHiIndicator: HiIndicator<*>? = null
    private var mLoop = true
    private var mAutoPlay = true
    private var mIntervalTime = 5000
    private var mOnBannerClickListener: IHiBanner.OnBannerClickListener? = null
    private var mOnPageChangeListener: ViewPager.OnPageChangeListener? = null
    private var mHiViewPager: HiViewPager? = null
    private var mScrollDuration = -1
    private val layoutParams =
        FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        if (mAdapter != null && mAdapter!!.getRealCount() > 0) {
            mOnPageChangeListener?.onPageScrolled(
                position % mAdapter!!.getRealCount(), positionOffset, positionOffsetPixels
            )
        }
    }

    override fun onPageSelected(position: Int) {
        if (mAdapter == null || mAdapter!!.getRealCount() == 0) return
        val realPosition = position % mAdapter!!.getRealCount()
        mOnPageChangeListener?.onPageSelected(realPosition)
        mHiIndicator?.onPointChange(realPosition, mAdapter!!.getRealCount())
    }

    override fun onPageScrollStateChanged(state: Int) {
        mOnPageChangeListener?.onPageScrollStateChanged(state)
    }

    override fun setBannerData(layoutResId: Int, models: List<HiBannerMo>) {
        mHiBannerMos = models
        init(layoutResId)
    }

    override fun setBannerData(models: List<HiBannerMo>) {
        setBannerData(R.layout.hi_banner_item_image, models)
    }

    override fun setHiIndicator(hiIndicator: HiIndicator<*>) {
        mHiIndicator = hiIndicator
        val childAt = hiBanner.getChildAt(1)
        if (childAt != null && mHiBannerMos != null) {
            hiBanner.removeView(childAt)
            mHiIndicator!!.onInflate(mHiBannerMos!!.size)
            hiBanner.addView(mHiIndicator!!.get(), layoutParams)
        }
    }

    override fun setAutoPlay(autoPlay: Boolean) {
        mAutoPlay = autoPlay
    }

    override fun setLoop(loop: Boolean) {
        mLoop = loop
    }

    override fun setIntervalTime(intervalTime: Int) {
        mIntervalTime = intervalTime
    }

    override fun setBindAdapter(bindAdapter: IBindAdapter) {
        mAdapter?.bindAdapter = bindAdapter
    }

    override fun setOnPageChangeListener(onPageChangeListener: ViewPager.OnPageChangeListener) {
        mOnPageChangeListener = onPageChangeListener
    }

    override fun setOnBannerClickListener(onBannerClickListener: IHiBanner.OnBannerClickListener) {
        mOnBannerClickListener = onBannerClickListener
    }

    override fun setScrollDuration(duration: Int) {
        mScrollDuration = duration
        mHiViewPager?.setScrollDuration(duration)
    }

    private fun init(layoutResId: Int) {
        if (mAdapter == null) {
            mAdapter = HiBannerAdapter(context, layoutResId)
        }
        if (mHiIndicator == null) {
            mHiIndicator = HiCircleIndicator(context)
        }

        mHiIndicator!!.onInflate(mHiBannerMos!!.size)

        with(mAdapter!!) {
            setBannerData(mHiBannerMos!!)
            loop = mLoop
            autoPlay = mAutoPlay
            bannerClickListener = mOnBannerClickListener
        }

        mHiViewPager = HiViewPager(context, null).apply {
            intervalTime = mIntervalTime.toLong()
            addOnPageChangeListener(this@HiBannerDelegate)
            autoPlay = mAutoPlay
            adapter = mAdapter
        }

        if (mScrollDuration > 0) mHiViewPager!!.setScrollDuration(mScrollDuration)

        if ((mAutoPlay || mLoop) && mAdapter!!.getRealCount() != 0) {
            //无限轮播关键点：使第一张能反向滑动到最后一张，已达到无限滚动的效果
            val firstItemPosition = mAdapter!!.getFirstItemPosition()
            mHiViewPager!!.setCurrentItem(firstItemPosition, false)
        }

        hiBanner.removeAllViews()
        hiBanner.addView(mHiViewPager, layoutParams)
        hiBanner.addView(mHiIndicator!!.get(), layoutParams)
    }
}