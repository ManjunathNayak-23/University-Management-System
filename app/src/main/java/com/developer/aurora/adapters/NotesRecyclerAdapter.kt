package com.developer.aurora.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.developer.aurora.R
import com.developer.aurora.databinding.NotesSingleRowBinding
import com.developer.aurora.notesRoom.Notes
import java.util.*


class NotesRecyclerAdapter(val listener: onNoteClick) :
    RecyclerView.Adapter<NotesRecyclerAdapter.NotesViewHolder>() {
    private var list: List<Notes> = emptyList()
    fun setData(list: List<Notes>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.notes_single_row, parent, false)
        return NotesViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.binding.NoteTitle.text = list[position].title
        val code = getRandomColor()
        holder.binding.cardView.setCardBackgroundColor(holder.view.resources.getColor(code, null))
        holder.binding.dateTxt.text = list[position].date

    }

    override fun getItemCount(): Int {
        return list.size
    }

    private fun getRandomColor(): Int {
        val colorCode: MutableList<Int> = ArrayList()

        colorCode.add(R.color.yellow)
        colorCode.add(R.color.skyblue)
        colorCode.add(R.color.lightPurple)
        colorCode.add(R.color.lightGreen)
        colorCode.add(R.color.gray)
        colorCode.add(R.color.pink)
        colorCode.add(R.color.red)

        val randomColor = Random()
        val number: Int = randomColor.nextInt(colorCode.size)
        return colorCode[number]
    }

    inner class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val binding = NotesSingleRowBinding.bind(itemView)
        val view = itemView

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position=bindingAdapterPosition
            listener.onClick(list[position])
        }
    }

     fun getNoteAt(position: Int):Notes{
return  list[position]
    }

    interface onNoteClick {
        fun onClick(notes: Notes)
    }
}