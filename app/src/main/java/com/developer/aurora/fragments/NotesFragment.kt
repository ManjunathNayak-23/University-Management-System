package com.developer.aurora.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.developer.aurora.R
import com.developer.aurora.adapters.NotesRecyclerAdapter
import com.developer.aurora.databinding.FragmentNotesBinding
import com.developer.aurora.notesRoom.Notes
import com.developer.aurora.notesRoom.NotesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class NotesFragment : Fragment(), NotesRecyclerAdapter.onNoteClick {
    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: NotesViewModel
    private lateinit var adapter: NotesRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(NotesViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = NotesRecyclerAdapter(this)
        binding.notesRecyclerview.adapter = adapter
        binding.notesRecyclerview.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
lifecycleScope.launch(Dispatchers.Main) {


        viewModel.readAllNotes.observe(viewLifecycleOwner, Observer { notes ->
            adapter.setData(notes)
            if (notes.isNotEmpty()) {
                binding.notesPlaceholderImg.visibility = View.INVISIBLE
                binding.notesPlaceholderTxt.visibility = View.INVISIBLE

            } else {
                binding.notesPlaceholderImg.visibility = View.VISIBLE
                binding.notesPlaceholderTxt.visibility = View.VISIBLE
            }

        })
}
        val callback: ItemTouchHelper.SimpleCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewModel.deleteNote(adapter.getNoteAt(viewHolder.bindingAdapterPosition))
            }


        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(binding.notesRecyclerview)

        binding.addNotesBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_notesFragment_to_addFragment)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(notes: Notes) {
        val title = notes.title
        val content = notes.content
        val id = notes.id
        val date = notes.date

        val action =
            NotesFragmentDirections.actionNotesFragmentToEditNoteFragment(id, title, content, date)
        findNavController().navigate(action)

    }

}