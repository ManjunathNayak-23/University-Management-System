package com.developer.aurora.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.developer.aurora.R
import com.developer.aurora.databinding.IdeasSingleRowBinding
import com.developer.aurora.models.IdeaModel

class IdeaRecyclerAdapter(private val list: List<IdeaModel>) :
    RecyclerView.Adapter<IdeaRecyclerAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.ideas_single_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.card.animation =
            AnimationUtils.loadAnimation(holder.itemView.context, R.anim.reycler_anim)
        holder.binding.txtIdea.text = list[position].idea
        holder.binding.txtName.text = list[position].name
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = IdeasSingleRowBinding.bind(itemView)

    }
}