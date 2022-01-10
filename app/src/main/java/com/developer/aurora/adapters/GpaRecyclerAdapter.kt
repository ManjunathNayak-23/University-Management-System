package com.developer.aurora.adapters

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.developer.aurora.R
import com.developer.aurora.databinding.GpaMarksSingleRowBinding
import com.developer.aurora.models.GpaModel

class GpaRecyclerAdapter(private val list: List<GpaModel>,val mCallback:OnItemClick) :
    RecyclerView.Adapter<GpaRecyclerAdapter.MyViewHolder>() {
var map:HashMap<Int,String> = HashMap()
    val gpaMap = HashMap<Int, String>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.gpa_marks_single_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.subjectName.text = list[position].subjectName
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = GpaMarksSingleRowBinding.bind(itemView)

        init {


            binding.creditsEntered.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    map[bindingAdapterPosition] = s.toString()

                    mCallback.onClick(map)
                }

                override fun afterTextChanged(s: Editable?) {

                }

            })
        }

    }
    interface OnItemClick {
        fun onClick(value:HashMap<Int,String>)
    }
}