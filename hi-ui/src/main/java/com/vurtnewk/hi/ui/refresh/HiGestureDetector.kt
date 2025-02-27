package com.vurtnewk.hi.ui.refresh

import android.view.GestureDetector
import android.view.MotionEvent

/**
 * author:      vurtnewk
 *
 * 目的是为了把一些方法 直接默认
 */
open class HiGestureDetector : GestureDetector.OnGestureListener {

    override fun onDown(e: MotionEvent): Boolean {
        return false
    }

    override fun onShowPress(e: MotionEvent) {
    }

    override fun onSingleTapUp(e: MotionEvent): Boolean {
        return false
    }

    override fun onScroll(e1: MotionEvent?, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
        return false
    }

    override fun onLongPress(e: MotionEvent) {
    }

    override fun onFling(e1: MotionEvent?, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
        return false
    }
}