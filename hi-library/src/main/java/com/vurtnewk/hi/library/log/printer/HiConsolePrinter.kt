package com.vurtnewk.hi.library.log.printer

import android.util.Log
import com.vurtnewk.hi.library.log.HiLogConfig

/**
 * author:      vurtnewk
 *
 * description: 控制台打印器
 */
class HiConsolePrinter : HiLogPrinter {

    override fun print(config: HiLogConfig, level: Int, tag: String, printString: String) {
        val countOfSub = printString.length / HiLogConfig.MAX_LEN
        if (countOfSub > 0) {
            val log = StringBuilder()
            var index = 0
            for (i in 0 until countOfSub) {
                log.append(printString.substring(index, index + HiLogConfig.MAX_LEN))
                index += HiLogConfig.MAX_LEN
            }
            // 如果不是整除, 加上最后的数据
            if (index != printString.length) {
                log.append(printString.substring(index, printString.length))
            }
            Log.println(level, tag, log.toString())
        } else {
            Log.println(level, tag, printString)
        }
    }
}