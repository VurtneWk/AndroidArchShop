package com.vurtnewk.hi.ui.refresh

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.widget.TextView


/**
 * author:      vurtnewk
 *
 *
 */
class HiTextOverView(context: Context, attrs: AttributeSet? = null) : HiOverView(context, attrs) {
    private lateinit var mText: TextView
    private lateinit var mRotateView: View

    override fun init() {
        LayoutInflater.from(context).inflate(com.vurtnewk.hi.ui.R.layout.hi_refresh_overview, this, true)
        mText = findViewById(com.vurtnewk.hi.ui.R.id.text)
        mRotateView = findViewById(com.vurtnewk.hi.ui.R.id.iv_rotate)
    }

    override fun onScroll(scrollY: Int, pullRefreshHeight: Int) {
    }

    override fun onVisible() {
        mText.text = "下拉刷新"
    }

    override fun onOver() {
        mText.text = "松开刷新"
    }

    override fun onRefresh() {
        mText.text = "正在刷新..."
        val operatingAnim: Animation = AnimationUtils.loadAnimation(context, com.vurtnewk.hi.ui.R.anim.rotate_anim)
        val lin = LinearInterpolator()
        operatingAnim.interpolator = lin
        mRotateView.startAnimation(operatingAnim)
    }

    override fun onFinish() {
        mRotateView.clearAnimation()
    }
}