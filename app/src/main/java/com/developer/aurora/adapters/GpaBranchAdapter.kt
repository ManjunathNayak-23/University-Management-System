package com.developer.aurora.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.developer.aurora.R
import com.developer.aurora.databinding.GpaBranchSingleRowBinding
import com.developer.aurora.models.GpaBranchModel

class GpaBranchAdapter(private val list: List<GpaBranchModel>, private val context: Context,val listener:onBranchClick) :
    RecyclerView.Adapter<GpaBranchAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.gpa_branch_single_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(context).load(list[position].image).transition(DrawableTransitionOptions.withCrossFade()).into(holder.binding.branchImage)
        holder.binding.branchName.text = list[position].branchName
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener {
        val binding = GpaBranchSingleRowBinding.bind(itemView)

        init {
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            val position=bindingAdapterPosition
            if(position!=RecyclerView.NO_POSITION){
                listener.onClick(list[position].branchName)
            }

        }

    }

    interface onBranchClick{
        fun onClick(branchName:String)
    }
}