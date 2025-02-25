package com.vurtnewk.hi.library.log.formatter

/**
 * author:      vurtnewk
 *
 * description: 堆栈信息格式化
 */
class HiStackTraceFormatter : HiLogFormatter<Array<StackTraceElement>> {

    override fun format(data: Array<StackTraceElement>): String? {
        val stringBuilder = StringBuilder(128) //128 是初始容量
        if (data.isEmpty()) {
            return null
        } else if (data.size == 1) {
            return "\t-${data[0]}"
        } else {
            data.forEachIndexed { index, stackTraceElement ->
                if (index == 0) {
                    stringBuilder.append("stackTrace:\n")
                }
                if (index != data.size - 1) {
                    stringBuilder.append("\t├ ")
                        .append(stackTraceElement.toString())
                        .append("\n")
                } else { //最后一个
                    stringBuilder.append("\t└ ")
                        .append(stackTraceElement.toString())
                }
            }
            return stringBuilder.toString()
        }
    }
}