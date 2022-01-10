package com.developer.aurora.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.developer.aurora.activities.AuthenticationActivity
import com.developer.aurora.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val sharedPrefFile = "loadProfile"
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireActivity().getSharedPreferences(
            sharedPrefFile,
            Context.MODE_PRIVATE
        )
        binding.userName.text = sharedPreferences.getString("name", "student").toString()
        binding.rollNo.text = sharedPreferences.getString("rollNo", "123").toString()
        binding.email.text = sharedPreferences.getString("email", "123").toString()
        binding.nameInitial.text=sharedPreferences.getString("name", "student").toString().substring(0,1)


        binding.signOut.setOnClickListener {
            auth = FirebaseAuth.getInstance()
            auth.signOut()
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putBoolean("isLoaded", false)
            editor.putString("rollNo","")
            editor.putString("name","")
            editor.putString("email`","")
            editor.apply()


            startActivity(Intent(requireActivity(), AuthenticationActivity::class.java))
            this.requireActivity().finishAffinity()
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}