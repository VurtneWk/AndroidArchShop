package com.vurtnewk.hi.ui.banner.indicator

import android.view.View

/**
 * author:      vurtnewk
 *
 * Indicator 接口
 */
interface HiIndicator<T : View> {

    fun get(): T

    /**
     * 初始化 indicator
     * @param count 幻灯片的数量
     */
    fun onInflate(count: Int)

    /**
     * 幻灯片切换回调
     * @param current 切换到的幻灯片位置
     * @param count   幻灯片数量
     */
    fun onPointChange(current: Int, count: Int)
}