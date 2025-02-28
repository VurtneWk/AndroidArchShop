package com.vurtnewk.hi.ui.banner.core

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.viewpager.widget.PagerAdapter

/**
 * author:      vurtnewk
 *
 *
 */
open class HiBannerAdapter(
    private val context: Context,
    @LayoutRes var layoutResId: Int,
    var bindAdapter: IBindAdapter? = null,
    var bannerClickListener: IHiBanner.OnBannerClickListener? = null
) : PagerAdapter() {

    var autoPlay = true
    var loop = false

    private var models = listOf<HiBannerMo>()
    private val mCacheView = SparseArray<HiBannerViewHolder>()

    //无限轮播关键点
    override fun getCount(): Int = if (autoPlay || loop) Int.MAX_VALUE else getRealCount()

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var realPosition = position
        if (getRealCount() > 0) {
            realPosition = position % getRealCount()
        }
        val viewHolder = mCacheView.get(realPosition)
        if (container == viewHolder.rootView.parent) {
            container.removeView(viewHolder.rootView)
        }
        onBind(viewHolder, models[realPosition], realPosition)
        viewHolder.rootView.parent?.let {
            (it as ViewGroup).removeView(viewHolder.rootView)
        }
        container.addView(viewHolder.rootView)
        return viewHolder.rootView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
//        super.destroyItem(container, position, `object`)
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE //让item每次都会刷新
    }

    fun getRealCount(): Int = models.size

    /**
     * 获取初次展示的item位置
     */
    fun getFirstItemPosition() = Int.MAX_VALUE / 2 - (Int.MAX_VALUE / 2) % getRealCount()

    fun setBannerData(models: List<HiBannerMo>) {
        this.models = models
        initCacheView()
    }

    private fun onBind(viewHolder: HiBannerViewHolder, hiBannerMo: HiBannerMo, position: Int) {
        viewHolder.rootView.setOnClickListener { bannerClickListener?.onBannerClick(viewHolder, hiBannerMo, position) }
        bindAdapter?.onBind(viewHolder, hiBannerMo, position)
    }

    private fun initCacheView() {
        mCacheView.clear()
        val inflater = LayoutInflater.from(context)
        for (index in models.indices) {
            val view = inflater.inflate(layoutResId, null, false)
            val viewHolder = HiBannerViewHolder(view)
            mCacheView.put(index, viewHolder)
        }
    }


    class HiBannerViewHolder(val rootView: View) {
        private var viewHolderSparseArr: SparseArray<View>? = null

        @Suppress("UNCHECKED_CAST")
        fun <T : View> findViewById(id: Int): T {
            if (rootView !is ViewGroup) return rootView as T
            if (viewHolderSparseArr == null)
                viewHolderSparseArr = SparseArray()

            var childView = viewHolderSparseArr!!.get(id)
            if (childView == null) {
                childView = rootView.findViewById(id)
                viewHolderSparseArr!!.put(id, childView)
            }
            return childView as T
        }
    }
}