package com.vurtnewk.hi.ui.refresh

/**
 * author:      vurtnewk
 *
 * HiRefresh 对外提供接口
 */
interface HiRefresh {

    /**
     * 刷新时是否禁止滚动
     * @param disableRefreshScroll false-禁止滚动
     */
    fun setDisableRefreshScroll(disableRefreshScroll: Boolean)

    /**
     * 刷新完成
     */
    fun refreshFinished()

    /**
     * 设置监听器
     */
    fun setRefreshListener(hiRefreshListener: HiRefreshListener)

    /**
     * 设置下拉刷新的视图
     */
    fun setRefreshOverView(hiOverView: HiOverView)


    /**
     * 刷新监听器
     */
    interface HiRefreshListener {
        /**
         * 是否开启下拉刷新
         */
        fun enableRefresh(): Boolean

        /**
         * 刷新
         */
        fun onRefresh()
    }
}