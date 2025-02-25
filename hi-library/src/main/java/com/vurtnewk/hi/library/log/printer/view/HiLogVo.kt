package com.vurtnewk.hi.library.log.printer.view

import java.text.SimpleDateFormat
import java.util.Locale


/**
 * author:      vurtnewk
 *
 * description:
 */
data class HiLogVo(
    val timeMillis: Long,
    val level: Int,
    val tag: String,
    val log: String
) {
    companion object {
        val sdf: SimpleDateFormat = SimpleDateFormat("yy-MM-dd HH:mm:ss", Locale.CHINA)
    }

    fun flattenedLog(): String = "${getFlattened()}\n$log"

    fun getFlattened(): String = "${format(timeMillis)}|$level|$tag|:"

    private fun format(timeMillis: Long): String {
        return sdf.format(timeMillis)
    }

}
