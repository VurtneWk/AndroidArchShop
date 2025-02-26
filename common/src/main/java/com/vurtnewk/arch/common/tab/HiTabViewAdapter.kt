package com.vurtnewk.arch.common.tab

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.vurtnewk.hi.ui.tab.bottom.HiTabBottomInfo

/**
 * author:      vurtnewk
 *
 * description:
 */
class HiTabViewAdapter(
    private val fragmentManager: FragmentManager,
    private val infoList: List<HiTabBottomInfo<*>>
) {

    var curFragment: Fragment? = null

    fun getCount(): Int = infoList.size

    /**
     * 实例化指定位置的fragment,并添加到container 容器上
     */
    fun instantiateItem(container: View, position: Int) {
        val transaction = fragmentManager.beginTransaction()
        // 先隐藏当前的 fragment
        if (curFragment != null) {
            transaction.hide(curFragment!!)
        }
        val tag = "${container.id}:$position"
        // 查找指定的 fragment
        var fragment = fragmentManager.findFragmentByTag(tag)
        // 如果找到了 直接显示
        if (fragment != null) {
            transaction.show(fragment)
        } else {
            fragment = getItem(position)
            fragment ?: return
            if (!fragment.isAdded) {
                transaction.add(container.id, fragment, tag)
            }
        }
        curFragment = fragment
        transaction.commitNowAllowingStateLoss()
    }

    /**
     * 反射创建指定的 Fragment , 失败则返回 null
     */
    private fun getItem(position: Int): Fragment? {
        try {
            return infoList[position].fragment?.getDeclaredConstructor()?.newInstance()
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

}