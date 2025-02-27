package com.vurtnewk.hi_ui_test.refresh

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.vurtnewk.hi.ui.refresh.HiRefresh
import com.vurtnewk.hi.ui.refresh.HiTextOverView
import com.vurtnewk.hi_ui_test.R
import com.vurtnewk.hi_ui_test.databinding.ActivityHiRefreshDemoBinding

class HiRefreshDemoActivity : AppCompatActivity() {

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, HiRefreshDemoActivity::class.java)
            context.startActivity(starter)
        }
    }

    private lateinit var activityHiRefreshDemoBinding: ActivityHiRefreshDemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        activityHiRefreshDemoBinding = ActivityHiRefreshDemoBinding.inflate(layoutInflater)
        setContentView(activityHiRefreshDemoBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(activityHiRefreshDemoBinding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        activityHiRefreshDemoBinding.apply {
            refreshLayout.mHiOverView = HiTextOverView(this@HiRefreshDemoActivity)
            refreshLayout.setRefreshListener(object : HiRefresh.HiRefreshListener {
                override fun enableRefresh(): Boolean {
                    return true
                }

                override fun onRefresh() {
                    refreshLayout.postDelayed({refreshLayout.refreshFinished()},1000)
                }
            })
        }
    }


}