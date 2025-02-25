package com.vurtnewk.hi.library.log.printer

import com.vurtnewk.hi.library.log.HiLogConfig

/**
 * author:      vurtnewk
 *
 * description: 日志打印接口
 */
interface HiLogPrinter {

    fun print(config: HiLogConfig, level: Int, tag: String, printString: String)
}