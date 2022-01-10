package com.developer.aurora.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.developer.aurora.R
import com.developer.aurora.databinding.LecturerSingleRowBinding
import com.developer.aurora.models.Lecturer

class LecturerRecyclerAdapter(private val list: List<Lecturer>,private val context:Context,val listener:onLecturrerCardClick) :
    RecyclerView.Adapter<LecturerRecyclerAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.lecturer_single_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.lecturerName.text = list[position].fullName
        holder.binding.lecturerSubject.text = list[position].subjects
        Glide.with(context).load(list[position].profilePio).transition(DrawableTransitionOptions.withCrossFade()).skipMemoryCache(false).diskCacheStrategy(
            DiskCacheStrategy.AUTOMATIC).into(holder.binding.lecturerImg)

    }

    override fun getItemCount(): Int {
        return list.size
    }

   inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener {
        val binding = LecturerSingleRowBinding.bind(itemView)

       init {
           itemView.setOnClickListener(this)
       }
       override fun onClick(v: View?) {
       listener.onClicked(list[bindingAdapterPosition])
       }
   }
    interface onLecturrerCardClick{
        fun onClicked(lecturer: Lecturer)
    }
}