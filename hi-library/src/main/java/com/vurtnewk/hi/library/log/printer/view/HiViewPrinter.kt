package com.vurtnewk.hi.library.log.printer.view

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vurtnewk.hi.library.R
import com.vurtnewk.hi.library.log.HiLogConfig
import com.vurtnewk.hi.library.log.HiLogManager.Companion.init
import com.vurtnewk.hi.library.log.HiLogType
import com.vurtnewk.hi.library.log.printer.HiLogPrinter

/**
 * author:      vurtnewk
 *
 * description:
 */
class HiViewPrinter(activity: Activity) : HiLogPrinter {

    private var mLogAdapter: LogAdapter
    private var mRecyclerView: RecyclerView
    val hiViewPrinterProvider: HiViewPrinterProvider

    init {
        val rootView = activity.findViewById<FrameLayout>(android.R.id.content)
        mLogAdapter = LogAdapter()
        mRecyclerView = RecyclerView(activity).also {
            it.layoutManager = LinearLayoutManager(activity)
            it.adapter = mLogAdapter
        }
        hiViewPrinterProvider = HiViewPrinterProvider(rootView, mRecyclerView)
    }

    override fun print(config: HiLogConfig, level: Int, tag: String, printString: String) {
        mLogAdapter.addItem(HiLogVo(System.currentTimeMillis(), level, tag, printString)) {
            mRecyclerView.smoothScrollToPosition(mLogAdapter.itemCount - 1)
        }
        // 异步计算的,这么直接写是无效的
//     mRecyclerView.smoothScrollToPosition(mLogAdapter.itemCount - 1)
    }


    private class LogAdapter : ListAdapter<HiLogVo, LogViewHolder>(COMPARATOR) {

        companion object {
            val COMPARATOR = object : DiffUtil.ItemCallback<HiLogVo>() {
                override fun areItemsTheSame(oldItem: HiLogVo, newItem: HiLogVo): Boolean {
                    return oldItem === newItem
                }

                override fun areContentsTheSame(oldItem: HiLogVo, newItem: HiLogVo): Boolean {
                    return oldItem == newItem
                }
            }
        }

        /**
         * @param commitCallback submitList 之后的回调
         */
        fun addItem(item: HiLogVo, commitCallback: Runnable) {
            val list = currentList.toMutableList()
            list.add(item)
            submitList(list, commitCallback)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_hi_log, parent, false)
            return LogViewHolder(view)
        }


        override fun onBindViewHolder(holder: LogViewHolder, position: Int) {
            val item = getItem(position)
            val highlightColor = getHighlightColor(item.level)
            holder.tagView.setTextColor(highlightColor)
            holder.messageView.setTextColor(highlightColor)
            holder.tagView.text = item.getFlattened()
            holder.messageView.text = item.log
        }

        private fun getHighlightColor(logLevel: Int): Int {
            return when (logLevel) {
                HiLogType.V -> 0xffbbbbbb.toInt()
                HiLogType.D -> 0xffffffff.toInt()
                HiLogType.I -> 0xff6a8759.toInt()
                HiLogType.W -> 0xffbbb529.toInt()
                HiLogType.E -> 0xffff6b68.toInt()
                else -> 0xffffff00.toInt()
            }
        }
    }

    private class LogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tagView = itemView.findViewById<TextView>(R.id.tag)
        val messageView = itemView.findViewById<TextView>(R.id.message)

    }


}