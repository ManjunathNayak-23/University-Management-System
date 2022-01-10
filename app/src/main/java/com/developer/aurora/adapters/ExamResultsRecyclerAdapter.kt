package com.developer.aurora.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.developer.aurora.R
import com.developer.aurora.databinding.ResultsSingleRowBinding
import com.developer.aurora.models.ExamResultsModel
import com.developer.aurora.models.Results

class ExamResultsRecyclerAdapter(private val resultsList: List<Results>,val listener:OnItemClick) :
    RecyclerView.Adapter<ExamResultsRecyclerAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.results_single_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.resultsCard.animation =
            AnimationUtils.loadAnimation(holder.itemView.context, R.anim.reycler_anim)
        holder.binding.examinationName.text = resultsList[position].examName
    }

    override fun getItemCount(): Int {
        return resultsList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener {
        val binding = ResultsSingleRowBinding.bind(itemView)
        init {
            binding.resultsCard.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            val position=bindingAdapterPosition
            if(position!=RecyclerView.NO_POSITION){
                listener.onClick(resultsList[position].examResultLink.toString())
            }
        }
    }

    interface OnItemClick {
        fun onClick(link:String)
    }
}