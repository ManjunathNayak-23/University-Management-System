package com.developer.aurora.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.developer.aurora.R
import com.developer.aurora.databinding.BusSelectionSingleRowBinding


class BusSelectionRecyclerviewAdapter(
    private val list: List<String>,
    private val listener: OnBusCardClick
) :
    RecyclerView.Adapter<BusSelectionRecyclerviewAdapter.SelectionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.bus_selection_single_row, parent, false)
        return SelectionViewHolder(view)
    }

    override fun onBindViewHolder(holder: SelectionViewHolder, position: Int) {
        holder.binding.busName.text = list[position]
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class SelectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val binding = BusSelectionSingleRowBinding.bind(itemView)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            listener.onClick(list[bindingAdapterPosition])
        }
    }

    interface OnBusCardClick {
        fun onClick(busName: String)
    }

}