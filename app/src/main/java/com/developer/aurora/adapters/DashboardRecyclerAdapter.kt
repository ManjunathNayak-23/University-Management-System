package com.developer.aurora.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.developer.aurora.R
import com.developer.aurora.databinding.DasboardSingleRowBinding
import com.developer.aurora.models.DashBoardItemModel

class DashboardRecyclerAdapter(
    private val list: List<DashBoardItemModel>,
    private val context: Context,val listener:onItemClick
) :
    RecyclerView.Adapter<DashboardRecyclerAdapter.DashboardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.dasboard_single_row, parent, false)
        return DashboardViewHolder(view)
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        holder.binding.card.animation =
            AnimationUtils.loadAnimation(holder.itemView.context, R.anim.reycler_anim)

        val currentItem = list[position]
        holder.binding.dbTitle.text = currentItem.heading
        holder.binding.dbTagLine.text = currentItem.tagLine

        Glide.with(context).load(currentItem.image).transition(DrawableTransitionOptions.withCrossFade()).skipMemoryCache(false).diskCacheStrategy(
            DiskCacheStrategy.AUTOMATIC).into(holder.binding.dbImg)

    }

    override fun getItemCount(): Int {
        return list.size
    }

   inner class DashboardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener {
        val binding = DasboardSingleRowBinding.bind(itemView)
      init {
          itemView.setOnClickListener(this)
      }

        override fun onClick(v: View?) {
            val position=bindingAdapterPosition
            if(position!=RecyclerView.NO_POSITION){
                listener.onCardClick(list[position].heading.lowercase())
            }

        }
    }
    interface onItemClick{
        fun onCardClick(title:String)
    }

}