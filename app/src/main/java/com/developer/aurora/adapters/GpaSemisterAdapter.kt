package com.developer.aurora.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.developer.aurora.R
import com.developer.aurora.databinding.GpaSemisterSingleRowBinding

class GpaSemisterAdapter(private val list: List<String>, private val listener: onYearClick) :
    RecyclerView.Adapter<GpaSemisterAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.gpa_semister_single_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.semister.text = list[position]
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val binding = GpaSemisterSingleRowBinding.bind(itemView)



        init {
            itemView.setOnClickListener(this)

        }

        override fun onClick(v: View?) {
            val position = bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onClick(position)
            }

        }

    }

    interface onYearClick {
        fun onClick(sem: Int)
    }
}