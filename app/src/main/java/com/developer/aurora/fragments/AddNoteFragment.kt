package com.developer.aurora.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.developer.aurora.databinding.FragmentAddBinding
import com.developer.aurora.notesRoom.Notes
import com.developer.aurora.notesRoom.NotesViewModel
import java.text.SimpleDateFormat
import java.util.*


class AddNoteFragment : Fragment() {
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    lateinit var navController: NavController
    private lateinit var viewModel: NotesViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(NotesViewModel::class.java)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        navController = findNavController()
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    saveTheContent()
                }
            })

        binding.toolbar.setNavigationOnClickListener {
            saveTheContent()
        }

    }

    private fun saveTheContent() {
        val title = binding.etTitle.text.toString()
        val content = binding.etContent.text.toString()
        if (title.trim().isNotEmpty() || content.trim().isNotEmpty()) {
            val time = System.currentTimeMillis()
            val simpleDateFormat = SimpleDateFormat("MMM dd,yyyy", Locale.ENGLISH)
            val dateString = simpleDateFormat.format(time)
            val note = Notes(0, title, content, dateString)
            viewModel.addNotes(note)
            Toast.makeText(requireContext(), "Note Added", Toast.LENGTH_SHORT)
                .show()
            navController.popBackStack()

        } else {
            navController.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}