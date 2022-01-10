package com.developer.aurora.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.developer.aurora.R
import com.developer.aurora.databinding.ColorSingleRowBinding
import com.developer.aurora.models.Colors

class ColorSelectorRecyclerAdapter(val list: List<Colors>, val listener: OnItemClick) :
    RecyclerView.Adapter<ColorSelectorRecyclerAdapter.ColorSelectorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorSelectorViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.color_single_row, parent, false)
        return ColorSelectorViewHolder(view)
    }

    override fun onBindViewHolder(holder: ColorSelectorViewHolder, position: Int) {
        val currentItem = list[position]
        holder.binding.view1.setBackgroundColor(Color.parseColor(currentItem.color1))
        holder.binding.view2.setBackgroundColor(Color.parseColor(currentItem.color2))
        holder.binding.view3.setBackgroundColor(Color.parseColor(currentItem.color3))


        holder.binding.view1.setOnClickListener {
            listener.onColorClick(currentItem.color1)
        }
        holder.binding.view2.setOnClickListener {
            listener.onColorClick(currentItem.color2)
        }
        holder.binding.view3.setOnClickListener {
            listener.onColorClick(currentItem.color3)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ColorSelectorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ColorSingleRowBinding.bind(itemView)
//        init {
////         binding.view1.setOnClickListener(this)
////         binding.view2.setOnClickListener(this)
////         binding.view3.setOnClickListener(this)
//            binding.linearLayout.setOnClickListener(this)
//        }
//        override fun onClick(p0: View?) {
//          listener.onColorClick(list[bindingAdapterPosition].color1)
//
//        }

    }

    interface OnItemClick {
        fun onColorClick(hex: String)

    }

}