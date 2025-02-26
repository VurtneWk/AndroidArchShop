package com.vurtnewk.hi_ui_test

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.vurtnewk.hi_ui_test.databinding.ActivityMainBinding
import com.vurtnewk.hi_ui_test.tab.HiTabBottomDemoActivity
import com.vurtnewk.hi_ui_test.tab.HiTabTopDemoActivity

class MainActivity : AppCompatActivity() {


    private lateinit var mActivityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mActivityMainBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        mActivityMainBinding.apply {
            btnToTabBottom.setOnClickListener { HiTabBottomDemoActivity.start(this@MainActivity) }
            btnToTabTop.setOnClickListener { HiTabTopDemoActivity.start(this@MainActivity) }
        }
    }
}