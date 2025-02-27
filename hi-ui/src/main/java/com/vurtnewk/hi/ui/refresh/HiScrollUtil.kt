package com.vurtnewk.hi.ui.refresh

import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.vurtnewk.hi.library.log.HiLog.d


/**
 * author:      vurtnewk
 *
 *
 */
internal object HiScrollUtil {

    fun findScrollableChild(viewGroup: ViewGroup): View {
        var child = viewGroup.getChildAt(1)
        if (child is RecyclerView || child is AdapterView<*>) {
            return child
        }
        if (child is ViewGroup) {
            val tempChild = child.getChildAt(0)
            if (tempChild is RecyclerView || tempChild is AdapterView<*>) {
                child = tempChild
            }
        }
        return child
    }

    /**
     * 判断view是否发生了滚动
     */
    fun scrolled(view: View): Boolean {
        if (view is AdapterView<*>) {
            if (view.firstVisiblePosition != 0 ||
                (view.firstVisiblePosition == 0 && view.getChildAt(0) != null && view.getChildAt(0).top < 0)
            ) {
                return true
            }
        } else if (view.scrollY > 0) {
            return true
        }
        if (view is RecyclerView) {
            val childView = view.getChildAt(0)
            val firstPosition = view.getChildAdapterPosition(childView)
            return firstPosition != 0 || view.top != 0
        }
        return false
    }

}