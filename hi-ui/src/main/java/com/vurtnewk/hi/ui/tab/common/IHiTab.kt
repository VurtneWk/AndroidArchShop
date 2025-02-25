package com.vurtnewk.hi.ui.tab.common

import androidx.annotation.Px

/**
 * author:      vurtnewk
 *
 * description:
 */
interface IHiTab<D> : IHiTabLayout.OnTabSelectedListener<D> {

    fun setHiTabInfo(data: D)

    /**
     * 动态修改某个item的大小
     */
    fun resetHeight(@Px height: Int)
}