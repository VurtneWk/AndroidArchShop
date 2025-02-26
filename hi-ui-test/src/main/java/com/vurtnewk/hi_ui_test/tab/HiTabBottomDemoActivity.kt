package com.vurtnewk.hi_ui_test.tab

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.get
import com.vurtnewk.hi.library.utils.dp2px
import com.vurtnewk.hi.ui.tab.bottom.HiTabBottomInfo
import com.vurtnewk.hi_ui_test.R
import com.vurtnewk.hi_ui_test.databinding.ActivityHiTabBottomDemoBinding
import com.vurtnewk.hi_ui_test.databinding.ActivityMainBinding

class HiTabBottomDemoActivity : AppCompatActivity() {

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, HiTabBottomDemoActivity::class.java)
            context.startActivity(starter)
        }
    }

    private lateinit var activityHiTabBottomDemoBinding: ActivityHiTabBottomDemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        activityHiTabBottomDemoBinding = ActivityHiTabBottomDemoBinding.inflate(layoutInflater)
        setContentView(activityHiTabBottomDemoBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.hi_tab_bottom_layout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val infoHome = HiTabBottomInfo(
            "首页", "fonts/iconfont.ttf", getString(R.string.if_home), null, "#FF656667", "#FFD44949"
        )

        val infoRecommend = HiTabBottomInfo(
            "收藏", "fonts/iconfont.ttf", getString(R.string.if_favorite), null, "#ff656667", "#ffd44949"
        )

//        val infoCategory = HiTabBottomInfo(
//            "分类", "fonts/iconfont.ttf", getString(R.string.if_category), null, "#ff656667", "#ffd44949"
//        )

        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.fire)

        val infoCategory = HiTabBottomInfo<String>(
            "分类",
            bitmap,
            bitmap
        )

        val infoChat = HiTabBottomInfo(
            "推荐", "fonts/iconfont.ttf", getString(R.string.if_recommend), null, "#ff656667", "#ffd44949"
        )
        val infoProfile = HiTabBottomInfo(
            "我的", "fonts/iconfont.ttf", getString(R.string.if_profile), null, "#ff656667", "#ffd44949"
        )
        val bottomInfoList = mutableListOf<HiTabBottomInfo<*>>()
        bottomInfoList.add(infoHome)
        bottomInfoList.add(infoRecommend)
        bottomInfoList.add(infoCategory)
        bottomInfoList.add(infoChat)
        bottomInfoList.add(infoProfile)
        activityHiTabBottomDemoBinding.apply {
            hiTabBottomLayout.inflateInfo(bottomInfoList)
            hiTabBottomLayout.addTabSelectedChangeListener { _, _, nextInfo ->
                Toast.makeText(this@HiTabBottomDemoActivity, nextInfo.name, Toast.LENGTH_SHORT).show()
            }
            hiTabBottomLayout.defaultSelected(infoHome)
            // 更改分类的高度
            hiTabBottomLayout.findTab(bottomInfoList[2])?.apply {
                resetHeight(dp2px(66F))
            }
        }
    }

}