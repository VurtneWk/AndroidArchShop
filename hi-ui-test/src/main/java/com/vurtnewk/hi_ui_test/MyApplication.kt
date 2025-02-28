package com.vurtnewk.hi_ui_test

import android.app.Application
import com.google.gson.Gson
import com.vurtnewk.hi.library.log.HiLogConfig
import com.vurtnewk.hi.library.log.HiLogManager
import com.vurtnewk.hi.library.log.printer.HiConsolePrinter

/**
 * author:      vurtnewk
 *
 * description:
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        HiLogManager.init(object : HiLogConfig() {
            override fun getGlobalTag(): String {
                return "MyApplication"
            }

            override fun enable(): Boolean {
                return true
            }

            override fun injectJsonParser(): JsonParser {
                return JsonParser { src -> Gson().toJson(src) }
            }
        }, HiConsolePrinter())
    }
}