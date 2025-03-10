package com.vurtnewk.hi.ui.tab.common

import android.view.ViewGroup

/**
 * author:      vurtnewk
 *
 * description:
 */
interface IHiTabLayout<Tab : ViewGroup, D> {

    fun findTab(data: D): Tab?

    fun addTabSelectedChangeListener(listener: OnTabSelectedListener<D>)

    fun defaultSelected(defaultInfo: D)

    fun inflateInfo(infoList: List<D>)

    fun interface OnTabSelectedListener<D> {
        fun onTabSelectedChange(index: Int, prevInfo: D?, nextInfo: D)
    }
}