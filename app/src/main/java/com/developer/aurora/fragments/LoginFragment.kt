package com.developer.aurora.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.developer.aurora.R
import com.developer.aurora.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signuptxt.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_loginFragment_to_registerFragment)
        }


        binding.loginBtn.setOnClickListener {
            val email = binding.loginEmail.text.toString()
            val password = binding.loginPassword.text.toString()

            verifyCredentials(email, password)

        }

        binding.forgotPassword.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }
    }

    private fun verifyCredentials(email: String, password: String) {
        if (email.isEmpty()) {
            binding.loginEmail.error = "Email is required"
            return
        }
        if (password.isEmpty()) {
            binding.loginPassword.error = "Password is required"
            return
        }
        loginTheUser(email, password)


    }

    private fun loginTheUser(email: String, password: String) {

        binding.loaderLayout.visibility = View.VISIBLE
        auth = FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                view?.let {
                    Navigation.findNavController(it)
                        .navigate(R.id.action_loginFragment_to_profileLoadFragment)
                }

            } else {
                binding.loaderLayout.visibility = View.GONE
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