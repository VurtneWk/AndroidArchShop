package com.vurtnewk.hi.library.log

import android.util.Log
import com.vurtnewk.hi.library.log.utils.HiStackTraceUtil


/**
 * author:      vurtnewk
 * description:
 * 1. 打印堆栈信息
 * 2. File输出
 * 3. 模拟控制台
 *
 * #### 注意点:
 * - kotlin 可变参数传递时,需要使用*展开,不然会被当做1个参数进行传递
 */
object HiLog {

    private val HI_LOG_PACKAGE = HiLog::class.java.`package`?.name

//    init {
//        val className = HiLog::class.java.name
//        HI_LOG_PACKAGE = className.substring(0, className.lastIndexOf(".") + 1)
//    }


    @JvmStatic
    fun v(vararg contents: Any) {
        log(HiLogType.V, *contents)
    }

    @JvmStatic
    fun vt(tag: String, vararg contents: Any) {
        log(HiLogType.V, tag, *contents)
    }

    @JvmStatic
    fun d(vararg contents: Any) {
        log(HiLogType.D, *contents)
    }

    @JvmStatic
    fun dt(tag: String, vararg contents: Any) {
        log(HiLogType.D, tag, *contents)
    }

    @JvmStatic
    fun i(vararg contents: Any) {
        log(HiLogType.I, *contents)
    }

    @JvmStatic
    fun it(tag: String, vararg contents: Any) {
        log(HiLogType.I, tag, *contents)
    }

    @JvmStatic
    fun w(vararg contents: Any) {
        log(HiLogType.W, *contents)
    }

    @JvmStatic
    fun wt(tag: String, vararg contents: Any) {
        log(HiLogType.W, tag, *contents)
    }

    @JvmStatic
    fun e(vararg contents: Any) {
        log(HiLogType.E, *contents)
    }

    @JvmStatic
    fun et(tag: String, vararg contents: Any) {
        log(HiLogType.E, tag, *contents)
    }

    @JvmStatic
    fun a(vararg contents: Any) {
        log(HiLogType.A, *contents)
    }

    @JvmStatic
    fun at(tag: String, vararg contents: Any) {
        log(HiLogType.A, tag, *contents)
    }

    @JvmStatic
    fun log(@HiLogType.TYPE type: Int, vararg contents: Any) {
        log(type, HiLogManager.getInstance().config.getGlobalTag(), *contents)
    }

    @JvmStatic
    fun log(@HiLogType.TYPE type: Int, tag: String, vararg contents: Any) {
        log(HiLogManager.getInstance().config, type, tag, *contents)
    }

    @JvmStatic
    fun log(config: HiLogConfig, @HiLogType.TYPE type: Int, tag: String, vararg contents: Any) {
        if (!config.enable()) {
            return
        }

        val sb = StringBuilder()
        // 线程信息
        if (config.includeThread()) {
            val threadInfo = HiLogConfig.HI_THREAD_FORMATTER.format(Thread.currentThread())
            sb.append(threadInfo).append("\n")
        }

        if (config.stackTraceDepth() > 0) {
            val realStackTrack = HiStackTraceUtil.getCroppedRealStackTrack(Throwable().stackTrace, HI_LOG_PACKAGE, 5)
            val stackTrace = HiLogConfig.HI_STACK_TRACE_FORMATTER.format(realStackTrack)
            sb.append(stackTrace).append("\n")
        }

        val body = parseBody(contents, config)
        sb.append(body)

        val printers = if (!config.printers().isNullOrEmpty()) {
            config.printers()
        } else {
            HiLogManager.getInstance().printers
        }
        printers ?: return
        printers.forEach {
            it.print(config, type, tag, sb.toString())
        }
    }


    private fun parseBody(contents: Array<out Any>, config: HiLogConfig): String {
        if (config.injectJsonParser() != null) {
            //只有一个数据且为String的情况下不再进行序列化
            if (contents.size == 1 && contents[0] is String) {
                return contents[0] as String
            }
            return config.injectJsonParser()!!.toJson(contents)
        }
        val sb = StringBuilder()
        contents.forEach {
            Log.d("TAG", "parseBody: $it")
            sb.append(it.toString()).append(";")
        }
        if (sb.isNotEmpty()) {
            sb.deleteCharAt(sb.length - 1)
        }
        return sb.toString()
    }


}
