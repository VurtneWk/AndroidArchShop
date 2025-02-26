package com.vurtnewk.arch

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.vurtnewk.arch.common.tab.HiTabViewAdapter
import com.vurtnewk.arch.databinding.ActivityMainBinding
import com.vurtnewk.arch.fragment.CategoryFragment
import com.vurtnewk.arch.fragment.FavoriteFragment
import com.vurtnewk.arch.fragment.HomePageFragment
import com.vurtnewk.arch.fragment.ProfileFragment
import com.vurtnewk.arch.fragment.RecommendFragment
import com.vurtnewk.hi.ui.tab.bottom.HiTabBottomInfo
import com.vurtnewk.hi.ui.tab.common.IHiTabLayout

class MainActivity : AppCompatActivity() {

    companion object {
        private const val SAVED_CURRENT_ID = "SAVED_CURRENT_ID"
    }

    private lateinit var activityMainBinding: ActivityMainBinding
    private val infoList = mutableListOf<HiTabBottomInfo<*>>()
    private var currentItemIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(activityMainBinding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // 可使用 开发者模式 里的 不保留活动进行测试
        // 不记录的话 会出现 fragment 重叠
        // **** Activity 重建时,fragment 也会重建,不过tag相同的情况下,fragmentManager获取的fragment的状态和之前一样(fragment对象不是同一个) ***
        if (savedInstanceState != null) {
            currentItemIndex = savedInstanceState.getInt(SAVED_CURRENT_ID)
        }
        initTabBottom()
    }

    private fun initTabBottom() {
        activityMainBinding.apply {
            tabBottomLayout.bottomAlpha = 0.85F
            val defaultColor = getColor(R.color.tabBottomDefaultColor)
            val tintColor = getColor(R.color.tabBottomTintColor)
            // https://blog.luckly-mjw.cn/tool-show/iconfont-preview/index.html
            // https://github.com/Momo707577045/iconfont-preview
            // 支持在线预览 ttf 文件
            HiTabBottomInfo(
                "首页", "fonts/iconfont.ttf", getString(R.string.if_home), null, defaultColor, tintColor
            ).apply {
                fragment = HomePageFragment::class.java
                infoList.add(this)
            }

            HiTabBottomInfo(
                "收藏", "fonts/iconfont.ttf", getString(R.string.if_favorite), null, defaultColor, tintColor
            ).apply {
                fragment = FavoriteFragment::class.java
                infoList.add(this)
            }

            HiTabBottomInfo(
                "分类", "fonts/iconfont.ttf", getString(R.string.if_category), null, defaultColor, tintColor
            ).apply {
                fragment = CategoryFragment::class.java
                infoList.add(this)
            }

            HiTabBottomInfo(
                "推荐", "fonts/iconfont.ttf", getString(R.string.if_recommend), null, defaultColor, tintColor
            ).apply {
                fragment = RecommendFragment::class.java
                infoList.add(this)
            }

            HiTabBottomInfo(
                "我的", "fonts/iconfont.ttf", getString(R.string.if_profile), null, defaultColor, tintColor
            ).apply {
                fragment = ProfileFragment::class.java
                infoList.add(this)
            }

            tabBottomLayout.inflateInfo(infoList)
            fragmentTabView.adapter = HiTabViewAdapter(supportFragmentManager, infoList)
            tabBottomLayout.addTabSelectedChangeListener { index, _, _ ->
                fragmentTabView.currentPosition = index
                currentItemIndex = index
            }
            tabBottomLayout.defaultSelected(infoList[currentItemIndex])
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SAVED_CURRENT_ID, currentItemIndex)
    }
}