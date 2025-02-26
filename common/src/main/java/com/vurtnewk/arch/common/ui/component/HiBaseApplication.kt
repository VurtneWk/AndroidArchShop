package com.vurtnewk.arch.common.ui.component

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
open class HiBaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initLog()
    }

    private fun initLog() {
        HiLogManager.init(object : HiLogConfig() {
            override fun injectJsonParser(): JsonParser {
                return JsonParser { src -> Gson().toJson(src) }
            }

            override fun includeThread(): Boolean {
                return true
            }
        }, HiConsolePrinter())

    }
}