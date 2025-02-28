package com.vurtnewk.hi.ui.banner.core

import androidx.annotation.LayoutRes
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.vurtnewk.hi.ui.banner.indicator.HiIndicator


/**
 * author:      vurtnewk
 *
 *
 */
interface IHiBanner {

    fun setBannerData(@LayoutRes layoutResId: Int, models: List<HiBannerMo>)

    fun setBannerData(models: List<HiBannerMo>)

    fun setHiIndicator(hiIndicator: HiIndicator<*>)

    fun setAutoPlay(autoPlay: Boolean)

    fun setLoop(loop: Boolean)

    fun setIntervalTime(intervalTime: Int)

    fun setBindAdapter(bindAdapter: IBindAdapter)

    fun setOnPageChangeListener(onPageChangeListener: OnPageChangeListener)

    fun setOnBannerClickListener(onBannerClickListener: OnBannerClickListener)

    fun setScrollDuration(duration: Int)

    interface OnBannerClickListener {
        fun onBannerClick(viewHolder: HiBannerAdapter.HiBannerViewHolder, bannerMo: HiBannerMo, position: Int)
    }
}