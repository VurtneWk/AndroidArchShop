package com.vurtnewk.hi.library.log

import android.util.Log
import androidx.annotation.IntDef

/**
 * author:      vurtnewk
 *
 * description:
 */
class HiLogType {
    @IntDef(V, D, I, W, E, A)
    @Retention(AnnotationRetention.SOURCE)
    annotation class TYPE

    companion object {
        const val V = Log.VERBOSE
        const val D = Log.DEBUG
        const val I = Log.INFO
        const val W = Log.WARN
        const val E = Log.ERROR
        const val A = Log.ASSERT
    }

}

