package com.developer.aurora.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.developer.aurora.R
import com.developer.aurora.databinding.EventsSingleRowBinding
import com.developer.aurora.models.Event

class EventRecyclerAdapter(
    private val eventList: List<Event>,
    private val context: Context,
    val listener: OnMoreBtnClick
) :
    RecyclerView.Adapter<EventRecyclerAdapter.EventRecyclerViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventRecyclerViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.events_single_row, parent, false)
        return EventRecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventRecyclerViewHolder, position: Int) {
        holder.binding.card.animation =
            AnimationUtils.loadAnimation(holder.itemView.context, R.anim.reycler_anim)
        val currentItem = eventList[position]
        Glide.with(context).load(currentItem.eventBannerUrl)
            .transition(DrawableTransitionOptions.withCrossFade()).into(holder.binding.eventBanner)
        holder.binding.eventName.text = currentItem.eventName
        holder.binding.eventDesc.text = currentItem.aboutEvent
        holder.binding.eventDate.text = currentItem.eventDateAndTime

    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    inner class EventRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val binding = EventsSingleRowBinding.bind(itemView)

        init {
            binding.moreDetailsBtn.setOnClickListener(this)

        }

        override fun onClick(v: View?) {
            listener.onClick(eventList[bindingAdapterPosition])
        }
    }

    interface OnMoreBtnClick {
        fun onClick(event: Event)
    }
}