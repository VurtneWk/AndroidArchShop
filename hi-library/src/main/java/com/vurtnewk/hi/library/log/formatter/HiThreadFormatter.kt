package com.vurtnewk.hi.library.log.formatter

/**
 * author:      vurtnewk
 *
 * description:
 */
class HiThreadFormatter : HiLogFormatter<Thread> {
    override fun format(data: Thread): String {
        return "Thread: ${data.name}"
    }
}