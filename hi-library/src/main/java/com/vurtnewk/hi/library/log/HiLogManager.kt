package com.vurtnewk.hi.library.log

import com.vurtnewk.hi.library.log.printer.HiLogPrinter


/**
 * author:      vurtnewk
 *
 * description:
 */
class HiLogManager private constructor(val config: HiLogConfig, val printers: MutableList<HiLogPrinter>) {


    companion object {
        private var instance: HiLogManager? = null

        fun getInstance(): HiLogManager {
            if (instance == null) {
                throw RuntimeException("HiLogManager must be initialized by calling init")
            }
            return instance!!
        }

        fun init(config: HiLogConfig, vararg printer: HiLogPrinter) {
            instance = HiLogManager(config, printer.toMutableList())
        }
    }

}