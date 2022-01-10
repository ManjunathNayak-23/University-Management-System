package com.developer.aurora.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.developer.aurora.R
import com.developer.aurora.databinding.PlacementsSingleRowBinding
import com.developer.aurora.models.Placement


class PlacementsRecyclerAdapter(
    private val list: List<Placement>,
    private val context: Context, val listener: OnApplyClick
) : RecyclerView.Adapter<PlacementsRecyclerAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.placements_single_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.binding.card.animation =
            AnimationUtils.loadAnimation(holder.itemView.context, R.anim.reycler_anim)
        val currentitem = list[position]
        holder.binding.companyName.text = currentitem.companyName
        holder.binding.moreDetails.text = currentitem.eligibleCriteria
        holder.binding.role.text = currentitem.jobPosition
        Glide.with(context).load(currentitem.companyImgUrl).into(holder.binding.companyImage)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val binding = PlacementsSingleRowBinding.bind(itemView)

        init {
            binding.applyBtn.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            listener.onClick(list[bindingAdapterPosition].applyLinkUrl.toString())
        }

    }

    interface OnApplyClick {
        fun onClick(link: String)
    }
}