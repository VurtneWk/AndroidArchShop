package com.vurtnewk.hi_libaray_test

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.vurtnewk.hi.library.log.HiLog
import com.vurtnewk.hi.library.log.HiLogConfig
import com.vurtnewk.hi.library.log.HiLogManager
import com.vurtnewk.hi.library.log.HiLogType
import com.vurtnewk.hi.library.log.printer.view.HiViewPrinter
import com.vurtnewk.hi_libaray_test.databinding.ActivityHiLogDemoBinding

class HiLogDemoActivity : AppCompatActivity() {

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, HiLogDemoActivity::class.java)
            context.startActivity(starter)
        }
    }

    private lateinit var mActivityHiLogDemoBinding: ActivityHiLogDemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mActivityHiLogDemoBinding = ActivityHiLogDemoBinding.inflate(layoutInflater)
        setContentView(mActivityHiLogDemoBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val hiViewPrinter = HiViewPrinter(this)
        HiLogManager.getInstance().printers.add(hiViewPrinter)
        hiViewPrinter.hiViewPrinterProvider.showFloatingView()

        mActivityHiLogDemoBinding.apply {
            btn01.setOnClickListener {
                HiLog.log(object : HiLogConfig() {
                    override fun includeThread(): Boolean {
                        return true
                    }

                    override fun stackTraceDepth(): Int {
                        return 0
                    }
                }, HiLogType.E, "-----", "5566")

                HiLog.d("9900")
            }
        }


    }
}