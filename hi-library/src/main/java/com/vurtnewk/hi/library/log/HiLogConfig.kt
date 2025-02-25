package com.vurtnewk.hi.library.log

import com.vurtnewk.hi.library.log.formatter.HiStackTraceFormatter
import com.vurtnewk.hi.library.log.formatter.HiThreadFormatter
import com.vurtnewk.hi.library.log.printer.HiLogPrinter


/**
 * author:      vurtnewk
 *
 * description:
 */
abstract class HiLogConfig {

    companion object {
        // 日志每一行的最大字符
        const val MAX_LEN = 512

        val HI_THREAD_FORMATTER: HiThreadFormatter = HiThreadFormatter()
        val HI_STACK_TRACE_FORMATTER: HiStackTraceFormatter = HiStackTraceFormatter()

    }


    /**
     * 默认不是 open 的
     */
    open fun getGlobalTag(): String = "HiLog"

    open fun enable(): Boolean {
        return true
    }

    open fun injectJsonParser(): JsonParser? = null
    open fun includeThread() = false
    open fun stackTraceDepth() = 5
    open fun printers(): MutableList<HiLogPrinter>? = null

    //fun interface 用于声明仅包含一个抽象方法的接口，使其可以直接用 Lambda 表达式实现
    fun interface JsonParser {
        fun toJson(src: Any): String
    }
}