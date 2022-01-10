package com.developer.aurora.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.developer.aurora.R
import com.developer.aurora.databinding.NoticeSingleRowBinding
import com.developer.aurora.models.Notice

class NoticeRecyclerAdapter(private val list: List<Notice>) :
    RecyclerView.Adapter<NoticeRecyclerAdapter.NoticeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.notice_single_row, parent, false)
        return NoticeViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoticeViewHolder, position: Int) {
        holder.binding.notice.text = list[position].notice
        holder.binding.noticeDate.text = list[position].time
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class NoticeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = NoticeSingleRowBinding.bind(itemView)

    }

}