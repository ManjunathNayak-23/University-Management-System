package com.developer.aurora.adapters

import android.animation.LayoutTransition
import android.graphics.Color
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.developer.aurora.R
import com.developer.aurora.databinding.ScheduleSingleRowBinding
import com.developer.aurora.scheduleRoom.Schedule

class ScheduleRecyclerAdapter(private val list: List<Schedule>, val listener: onEditClick) :
    RecyclerView.Adapter<ScheduleRecyclerAdapter.ScheduleViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.schedule_single_row, parent, false)
        return ScheduleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        val currentItem = list[position]

        holder.binding.subjectTime.text = currentItem.startTime
        holder.binding.subjectTitle.text = currentItem.title

        holder.binding.card.setBackgroundColor(Color.parseColor(currentItem.color))

        holder.binding.subjectTitle.setOnClickListener {
            if (!currentItem.isExpanded) {

                currentItem.isExpanded = true
                TransitionManager.beginDelayedTransition(
                    holder.binding.expandableLayout,
                    AutoTransition()
                )
                holder.binding.expandableLayout.visibility = View.VISIBLE
            } else {
                TransitionManager.beginDelayedTransition(
                    holder.binding.expandableLayout,
                    AutoTransition()
                )
                holder.binding.expandableLayout.visibility = View.GONE
                currentItem.isExpanded = false


            }
        }


    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ScheduleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val binding = ScheduleSingleRowBinding.bind(itemView)


        init {

            binding.edit.setOnClickListener(this)
            binding.expandableLayout.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        }

        override fun onClick(p0: View?) {
            val currentPos = list[bindingAdapterPosition]
            val schedule = Schedule(
                currentPos.id,
                currentPos.day,
                currentPos.title,
                currentPos.startTime,
                currentPos.endTime,
                currentPos.color,
                currentPos.info
            )
            listener.onClick(schedule)
        }

    }

    interface onEditClick {
        fun onClick(schedule: Schedule)
    }

}