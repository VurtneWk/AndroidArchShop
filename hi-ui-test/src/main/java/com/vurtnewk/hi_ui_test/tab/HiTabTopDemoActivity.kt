package com.vurtnewk.hi_ui_test.tab

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.vurtnewk.hi.ui.tab.top.HiTabTopInfo
import com.vurtnewk.hi_ui_test.R
import com.vurtnewk.hi_ui_test.databinding.ActivityHiTabTopDemoBinding


class HiTabTopDemoActivity : AppCompatActivity() {


    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, HiTabTopDemoActivity::class.java)
            context.startActivity(starter)
        }
    }

    private lateinit var activityHiTabTopDemoBinding: ActivityHiTabTopDemoBinding

    var tabsStr: Array<String> = arrayOf(
        "热门",
        "服装",
        "数码",
        "鞋子",
        "零食",
        "家电",
        "汽车",
        "百货",
        "家居",
        "装修",
        "运动",
        "热门",
        "服装",
        "数码",
        "鞋子",
        "零食",
        "家电",
        "汽车",
        "百货",
        "家居",
        "装修",
        "运动"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        activityHiTabTopDemoBinding = ActivityHiTabTopDemoBinding.inflate(layoutInflater)
        setContentView(activityHiTabTopDemoBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(activityHiTabTopDemoBinding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initTabTop()
    }

    private fun initTabTop() {
        val defaultColor = getColor(R.color.tabBottomDefaultColor)
        val tintColor = getColor(R.color.tabBottomTintColor)
        val infoList = mutableListOf<HiTabTopInfo<*>>()
        tabsStr.forEach {
            infoList.add(HiTabTopInfo(it, defaultColor, tintColor))
        }
        activityHiTabTopDemoBinding.tabTopLayout.inflateInfo(infoList)
        activityHiTabTopDemoBinding.tabTopLayout.addTabSelectedChangeListener { index, prev, next ->
            Toast.makeText(this, next.name, Toast.LENGTH_SHORT).show()
        }
        activityHiTabTopDemoBinding.tabTopLayout.defaultSelected(infoList[0])
    }
}