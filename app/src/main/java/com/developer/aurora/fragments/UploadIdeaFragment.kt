package com.developer.aurora.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.developer.aurora.databinding.FragmentUploadIdeaBinding
import com.developer.aurora.models.IdeaModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class UploadIdeaFragment : Fragment() {
    private var _binding: FragmentUploadIdeaBinding? = null
    private val binding get() = _binding!!
    private lateinit var reference: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUploadIdeaBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }

        binding.uploadBtn.setOnClickListener {
            val idea = binding.idea.text.toString()

            if (idea.isEmpty()) {
                Toast.makeText(requireActivity(), "Idea cannot be empty", Toast.LENGTH_SHORT).show()
            } else {
                uploadIdea(idea)
            }
        }

    }

    private fun uploadIdea(idea: String) {
        binding.loaderLayout.visibility = View.VISIBLE
        auth = FirebaseAuth.getInstance()
        reference = FirebaseDatabase.getInstance().reference
        val model = IdeaModel(idea, auth.currentUser?.displayName.toString())
        reference.child("Ideas").push().setValue(model).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(requireActivity(), "Successfully posted", Toast.LENGTH_SHORT).show()
                view?.let { Navigation.findNavController(it).navigateUp() }
            } else {
                Toast.makeText(requireActivity(), task.exception.toString(), Toast.LENGTH_SHORT)
                    .show()

            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}