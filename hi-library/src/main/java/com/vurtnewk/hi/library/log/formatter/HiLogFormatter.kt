package com.vurtnewk.hi.library.log.formatter

/**
 * author:      vurtnewk
 *
 * description: 日志格式化接口
 */
interface HiLogFormatter<T> {

    fun format(data: T): String?
}