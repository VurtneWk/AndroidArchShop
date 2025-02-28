package com.vurtnewk.hi_ui_test.banner

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.vurtnewk.hi.ui.banner.core.HiBannerMo
import com.vurtnewk.hi.ui.banner.indicator.HiCircleIndicator
import com.vurtnewk.hi.ui.banner.indicator.HiIndicator
import com.vurtnewk.hi.ui.banner.indicator.HiNumIndicator
import com.vurtnewk.hi_ui_test.R
import com.vurtnewk.hi_ui_test.databinding.ActivityHiBannerDemoBinding

class HiBannerDemoActivity : AppCompatActivity() {

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, HiBannerDemoActivity::class.java)
            context.startActivity(starter)
        }
    }

    private lateinit var activityHiBannerDemoBinding: ActivityHiBannerDemoBinding

    private var urls = arrayOf(
        "https://www.devio.org/img/beauty_camera/beauty_camera1.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera3.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera4.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera5.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera2.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera6.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera7.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera8.jpeg"
    )
    private var autoPlay = true
    private var mHiIndicator: HiIndicator<*>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        activityHiBannerDemoBinding = ActivityHiBannerDemoBinding.inflate(layoutInflater)
        setContentView(activityHiBannerDemoBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(activityHiBannerDemoBinding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initView()
    }

    private fun initView() {
        mHiIndicator = HiCircleIndicator(this)
        initBanner(mHiIndicator, autoPlay)
        activityHiBannerDemoBinding.autoPlay.setOnCheckedChangeListener { _, isChecked ->
            autoPlay = isChecked
            initBanner(mHiIndicator, autoPlay)
        }
        activityHiBannerDemoBinding.tvSwitch.setOnClickListener {
            if (mHiIndicator is HiNumIndicator) {
                mHiIndicator = HiCircleIndicator(this@HiBannerDemoActivity)
            } else if (mHiIndicator is HiCircleIndicator) {
                mHiIndicator = HiNumIndicator(this@HiBannerDemoActivity)
            }
            initBanner(mHiIndicator, autoPlay)
        }
    }

    private fun initBanner(hiIndicator: HiIndicator<*>?, autoPlay: Boolean) {
        val moList: MutableList<HiBannerMo> = ArrayList()
        for (i in 0..5) {
            val mo = BannerMo(urls[i % urls.size])
            moList.add(mo)
        }
        activityHiBannerDemoBinding.apply {
            banner.setAutoPlay(autoPlay)
            banner.setIntervalTime(2000)

            banner.setScrollDuration(1000)

            banner.setBannerData(R.layout.banner_item_layout, moList)
            banner.setHiIndicator(hiIndicator!!)

            banner.setBindAdapter { viewHolder, mo, _ ->
                val imageView = viewHolder.findViewById<ImageView>(R.id.iv_image)
                Glide.with(this@HiBannerDemoActivity).load(mo.url).into(imageView)
                val textView = viewHolder.findViewById<TextView>(R.id.tv_title)
                textView.text = mo.url
            }
        }
    }
}