package com.vurtnewk.arch.common.tab

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.fragment.app.Fragment

/**
 * author:      vurtnewk
 *
 * description: 这是用来放具体内容的视图,通过 HiTabViewAdapter 来把具体 fragment 显示出来的
 */
class HiFragmentTabView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    var adapter: HiTabViewAdapter? = null
        set(value) {
            if (value != null && field == null) {
                field = value
            }
        }

    var currentPosition = -1
        set(value) {
            if (adapter != null && value >= 0 && value < adapter!!.getCount()) {
                field = value
                adapter!!.instantiateItem(this, value)
            }
        }

    fun getCurrentFragment(): Fragment? {
        if (adapter == null) throw IllegalArgumentException("please call setAdapter first.")
        return adapter!!.curFragment
    }
}