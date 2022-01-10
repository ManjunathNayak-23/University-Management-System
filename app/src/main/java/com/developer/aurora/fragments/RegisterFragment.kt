package com.developer.aurora.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.developer.aurora.R
import com.developer.aurora.activities.MainActivity
import com.developer.aurora.databinding.FragmentRegisterBinding
import com.developer.aurora.models.Register
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.auth.UserProfileChangeRequest

import com.google.firebase.auth.FirebaseUser





class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var reference: DatabaseReference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        _binding = FragmentRegisterBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val year = resources.getStringArray(R.array.year)
        val sem = resources.getStringArray(R.array.sem)

        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, year)
        val semArrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, sem)
        binding.yearAutoComplete.setAdapter(arrayAdapter)
        binding.semAutoComplete.setAdapter(semArrayAdapter)

        binding.loginTxt.setOnClickListener {

            Navigation.findNavController(view).navigateUp()
        }

        binding.signUpBtn.setOnClickListener {
            val name = binding.regName.text.toString()
            val rollNo = binding.regRollNo.text.toString()
            val year = binding.yearAutoComplete.text.toString()
            val sem = binding.semAutoComplete.text.toString()
            val branch = binding.regBranch.text.toString()
            val email = binding.regEmail.text.toString()
            val password = binding.regPassword.text.toString()

            verifyCredentials(name, rollNo, year, sem, branch, email, password)


        }


    }

    private fun verifyCredentials(
        name: String,
        rollNo: String,
        year: String,
        sem: String,
        branch: String,
        email: String,
        password: String
    ) {
        if (name.isEmpty()) {
            binding.regName.error = "Name is required"
            return
        }
        if (rollNo.isEmpty()) {
            binding.regRollNo.error = "RollNo is required"
            return
        }
        if (branch.isEmpty()) {
            binding.regBranch.error = "Branch is required"
            return
        }
        if (email.isEmpty()) {
            binding.regEmail.error = "Email is required"
            return
        }
        if (password.isEmpty()) {
            binding.regPassword.error = "Password is required"
            return
        }
        if (password.length < 6) {
            binding.regPassword.error = "Password need to be 6 char long"
            return
        }
        if (year == "Current Year") {
            binding.yearAutoComplete.error = "Select Year"
            return
        }
        if (sem == "Current Sem") {
            binding.yearAutoComplete.error = "Select sem"
            return
        }
        registerUser(name, rollNo, year, sem, branch, email, password)


    }

    private fun registerUser(
        name: String,
        rollNo: String,
        year: String,
        sem: String,
        branch: String,
        email: String,
        password: String
    ) {
        binding.loaderLayout.visibility = View.VISIBLE
        auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = FirebaseAuth.getInstance().currentUser

                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(name).build();
                user?.updateProfile(profileUpdates)?.addOnCompleteListener { task->
                    if(task.isSuccessful){
                        uploadStudentDetailsInDB(name, rollNo, year, sem, branch, email)
                    }else{
                        Toast.makeText(requireActivity(),task.exception.toString(),Toast.LENGTH_SHORT).show()
                    }

                }


            } else {
                Toast.makeText(
                    requireContext(),
                    "Unable to register you" + task.exception.toString(),
                    Toast.LENGTH_LONG
                ).show()
                binding.loaderLayout.visibility = View.GONE
            }
        }
    }


    private fun uploadStudentDetailsInDB(
        name: String,
        rollNo: String,
        year: String,
        sem: String,
        branch: String,
        email: String
    ) {
        val user =
            Register(name, email, rollNo, branch, sem, year, auth.currentUser?.uid.toString())
        reference = FirebaseDatabase.getInstance().reference
        reference.child("Students").child(auth.currentUser?.uid.toString()).setValue(user)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    view?.let { Navigation.findNavController(it).navigate(R.id.action_registerFragment_to_profileLoadFragment) }

                } else {
                    Toast.makeText(
                        requireContext(),
                        task.exception.toString(),
                        Toast.LENGTH_LONG
                    ).show()

                    binding.loaderLayout.visibility = View.GONE
                }
            }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}