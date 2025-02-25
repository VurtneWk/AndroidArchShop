package com.vurtnewk.hi.ui.tab.bottom

import android.graphics.Bitmap
import androidx.fragment.app.Fragment

/**
 * author:      vurtnewk
 *
 * description:
 */
class HiTabBottomInfo<Color> {

    enum class TabType {
        BITMAP, ICON
    }

    var fragment: Class<out Fragment>? = null
    var name: String
    var defaultBitmap: Bitmap? = null
    var selectedBitmap: Bitmap? = null
    var iconFont: String? = null

    /**
     * Tips：在Java代码中直接设置 iconfont 字符串无效，需要定义在 string.xml
     */
    var defaultIconName: String? = null
    var selectedIconName: String? = null
    var defaultColor: Color? = null
    var tintColor: Color? = null
    var tabType: TabType

    constructor(name: String, defaultBitmap: Bitmap?, selectedBitmap: Bitmap?) {
        this.name = name
        this.defaultBitmap = defaultBitmap
        this.selectedBitmap = selectedBitmap
        this.tabType = TabType.BITMAP
    }

    constructor(
        name: String,
        iconFont: String?,
        defaultIconName: String?,
        selectedIconName: String?,
        defaultColor: Color,
        tintColor: Color
    ) {
        this.name = name
        this.iconFont = iconFont
        this.defaultIconName = defaultIconName
        this.selectedIconName = selectedIconName
        this.defaultColor = defaultColor
        this.tintColor = tintColor
        this.tabType = TabType.ICON
    }
}