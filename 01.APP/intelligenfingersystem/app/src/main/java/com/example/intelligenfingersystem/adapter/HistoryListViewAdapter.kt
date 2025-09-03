package com.example.intelligenfingersystem.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.intelligenfingersystem.databinding.HistoryListItemBinding
import com.example.intelligenfingersystem.db.HistoryDao
import com.example.intelligenfingersystem.entity.History


class HistoryListViewAdapter(
    private val context: Context,
    private var listData: MutableList<Any>
) : BaseAdapter() {
    private val dao = HistoryDao(context)
    override fun getCount(): Int {
        return listData.size
    }

    override fun getItem(i: Int): Any {
        return listData[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getView(i: Int, p0: View?, viewGroup: ViewGroup): View {
        var view = p0
        val holder: ViewHolder
        if (view == null) {
            val binding = HistoryListItemBinding.inflate(
                LayoutInflater.from(
                    context
                ), viewGroup, false
            )
            view = binding.root
            holder = ViewHolder(binding)
            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }
        initView(holder.binding, i)
        return view
    }

    private fun initView(binding: HistoryListItemBinding, index: Int) {
        val history = listData[index] as History
        binding.nameText.text = history.user
        binding.nameText.visibility = View.GONE
        binding.openTime.text = "开锁时间:${history.createDateTime}"
    }

    class ViewHolder(var binding: HistoryListItemBinding)
}