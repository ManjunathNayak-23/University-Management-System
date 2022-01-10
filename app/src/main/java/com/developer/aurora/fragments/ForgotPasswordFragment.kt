package com.developer.aurora.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.developer.aurora.databinding.FragmentForgotPasswordBinding
import com.google.firebase.auth.FirebaseAuth


class ForgotPasswordFragment : Fragment() {
    private var _binding: FragmentForgotPasswordBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        _binding = FragmentForgotPasswordBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()

        binding.resetBtn.setOnClickListener {
            val email = binding.resetEmail.text.toString()
            if (email.isEmpty()) {
                Toast.makeText(requireContext(), "Email is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            sendResetEmail(email)
        }

        binding.toolbar.setNavigationOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }
    }

    private fun sendResetEmail(email: String) {
        binding.loaderLayout.visibility = View.VISIBLE
        auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(requireActivity(), "Reset Mail sent successfully", Toast.LENGTH_LONG)
                    .show()
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