package com.vurtnewk.hi.library.utils

import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.core.view.indices

/**
 * author:      vurtnewk
 *
 * description:
 */

/**
 * 在 指定布局上 查找 指定类型的子View
 */
fun <T> findTypeView(group: ViewGroup?, cls: Class<T>): T? {
    group ?: return null
    val deque = ArrayDeque<View>()
    deque.add(group)
    while (deque.isNotEmpty()) {
        val node = deque.removeFirst()
        // 如果这个class 是 node 类型的view
        if (cls.isInstance(node)) {
            return cls.cast(node)
        } else if (node is ViewGroup) {//如果这个node还是ViewGroup
            for (index in node.indices) {
                deque.add(node[index])
            }
        }
    }
    return null
}