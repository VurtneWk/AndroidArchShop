package com.vurtnewk.hi.library.log.utils

import kotlin.math.min

/**
 * author:      vurtnewk
 *
 * description:
 */
object HiStackTraceUtil {

    /**
     * @param stackTrace 堆栈信息
     * @param ignorePackage 忽略的包名
     * @param maxDepth 最大深度
     */
    fun getCroppedRealStackTrack(stackTrace: Array<StackTraceElement>, ignorePackage: String?, maxDepth: Int): Array<StackTraceElement> {
        return cropStackTrace(getRealStackTrack(stackTrace, ignorePackage), maxDepth)
    }

    private fun getRealStackTrack(stackTrace: Array<StackTraceElement>, ignorePackage: String?): Array<StackTraceElement> {
        var ignoreDepth = 0
        val allDepth = stackTrace.size
        var className: String
        // 设置忽略的部分
        for (i in allDepth - 1 downTo 0) {
            className = stackTrace[i].className
            if (ignorePackage != null && className.startsWith(ignorePackage)) {
                ignoreDepth = i + 1
                break
            }
        }
        return stackTrace.copyOfRange(ignoreDepth, allDepth)
    }

    /**
     * 裁剪堆栈信息
     */
    private fun cropStackTrace(callStack: Array<StackTraceElement>, maxDepth: Int): Array<StackTraceElement> {
        var realDepth = callStack.size
        if (maxDepth > 0) {
            realDepth = min(maxDepth.toDouble(), realDepth.toDouble()).toInt()
        }
        return callStack.copyOfRange(0, realDepth)
    }

}