package com.vurtnewk.hi_ui_test.tab

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val hiTabBottomInfo = HiTabBottomInfo(
            "首页", "fonts/iconfont.ttf", getString(R.string.if_home),
            null, "#FF656667", "#FFD44949"
        )
        activityHiTabBottomDemoBinding.hiTabBottom.setHiTabInfo(hiTabBottomInfo)

    }

}