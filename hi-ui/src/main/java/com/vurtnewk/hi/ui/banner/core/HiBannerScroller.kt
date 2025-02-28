package com.vurtnewk.hi.ui.banner.core

import android.content.Context
import android.widget.Scroller

/**
 * author:      vurtnewk
 *
 * @param duration 值越大，滑动越慢
 */
class HiBannerScroller(context: Context, private val duration: Int = 1000) : Scroller(context) {

    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int) {
        super.startScroll(startX, startY, dx, dy, duration)
    }

    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
        super.startScroll(startX, startY, dx, dy, this.duration)
    }
}