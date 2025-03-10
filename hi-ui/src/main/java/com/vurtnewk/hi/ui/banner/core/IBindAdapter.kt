package com.vurtnewk.hi.ui.banner.core

/**
 * author:      vurtnewk
 *
 * HiBanner 的数据绑定接口，基于该接口可以实现数据的绑定和框架层解耦
 */
fun interface IBindAdapter {

    fun onBind(viewHolder: HiBannerAdapter.HiBannerViewHolder, mo: HiBannerMo, position: Int)
}