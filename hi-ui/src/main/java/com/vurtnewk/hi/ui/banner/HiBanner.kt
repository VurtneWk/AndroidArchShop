package com.vurtnewk.hi.ui.banner

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.viewpager.widget.ViewPager
import com.vurtnewk.hi.ui.banner.core.HiBannerDelegate
import com.vurtnewk.hi.ui.banner.core.HiBannerMo
import com.vurtnewk.hi.ui.banner.core.IBindAdapter
import com.vurtnewk.hi.ui.banner.core.IHiBanner
import com.vurtnewk.hi.ui.banner.indicator.HiIndicator


/**
 * author:      vurtnewk
 *
 * 核心问题：
 * 1. 如何实现UI的高度定制？
 * 2. 作为有限的item如何实现无线轮播呢？
 * 3. Banner需要展示网络图片，如何将网络图片库和Banner组件进行解耦？
 * 4. 指示器样式各异，如何实现指示器的高度定制？
 * 5. 如何设置ViewPager的滚动速度？
 */
class HiBanner @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs), IHiBanner {

    private val delegate: HiBannerDelegate = HiBannerDelegate(context, this)

    init {
        val typedArray = context.obtainStyledAttributes(attrs, com.vurtnewk.hi.ui.R.styleable.HiBanner)
        val autoPlay = typedArray.getBoolean(com.vurtnewk.hi.ui.R.styleable.HiBanner_autoPlay, true)
        val loop = typedArray.getBoolean(com.vurtnewk.hi.ui.R.styleable.HiBanner_loop, false)
        val intervalTime = typedArray.getInteger(com.vurtnewk.hi.ui.R.styleable.HiBanner_intervalTime, -1)
        setAutoPlay(autoPlay)
        setLoop(loop)
        setIntervalTime(intervalTime)
        typedArray.recycle()
    }

    override fun setBannerData(layoutResId: Int, models: List<HiBannerMo>) {
        delegate.setBannerData(layoutResId, models)
    }

    override fun setBannerData(models: List<HiBannerMo>) {
        delegate.setBannerData(models)
    }

    override fun setHiIndicator(hiIndicator: HiIndicator<*>) {
        delegate.setHiIndicator(hiIndicator)
    }

    override fun setAutoPlay(autoPlay: Boolean) {
        delegate.setAutoPlay(autoPlay)
    }

    override fun setLoop(loop: Boolean) {
        delegate.setLoop(loop)
    }

    override fun setIntervalTime(intervalTime: Int) {
        delegate.setIntervalTime(intervalTime)
    }

    override fun setBindAdapter(bindAdapter: IBindAdapter) {
        delegate.setBindAdapter(bindAdapter)
    }

    override fun setOnPageChangeListener(onPageChangeListener: ViewPager.OnPageChangeListener) {
        delegate.setOnPageChangeListener(onPageChangeListener)
    }

    override fun setOnBannerClickListener(onBannerClickListener: IHiBanner.OnBannerClickListener) {
        delegate.setOnBannerClickListener(onBannerClickListener)
    }

    override fun setScrollDuration(duration: Int) {
        delegate.setScrollDuration(duration)
    }
}