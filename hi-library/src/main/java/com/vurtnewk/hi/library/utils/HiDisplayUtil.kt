package com.vurtnewk.hi.library.utils

import android.content.res.Resources
import android.util.TypedValue

/**
 * author:      vurtnewk
 *
 * description:
 */

fun dp2px(dp: Float) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().displayMetrics).toInt()

fun getDisplayWidthPx() = Resources.getSystem().displayMetrics.widthPixels

fun getScreenHeightPx() = Resources.getSystem().displayMetrics.heightPixels