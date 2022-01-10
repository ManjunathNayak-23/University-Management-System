package com.developer.aurora.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.developer.aurora.R
import com.developer.aurora.activities.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class ProfileLoadFragment : Fragment() {
    private val sharedPrefFile = "loadProfile"
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var auth: FirebaseAuth
    private lateinit var reference: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_load, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireActivity().getSharedPreferences(
            sharedPrefFile,
            Context.MODE_PRIVATE
        )
        fetchStudentData()
    }

    private fun fetchStudentData() {

        try {


            auth = FirebaseAuth.getInstance()

            reference = FirebaseDatabase.getInstance().reference
            reference.child("Students").child(auth.currentUser?.uid.toString())
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {


                      val  studentRollNo = snapshot.child("rollNo").value.toString()
                       val studentName = snapshot.child("name").value.toString()
                        val studentEmail = snapshot.child("email").value.toString()
                        val editor: SharedPreferences.Editor = sharedPreferences.edit()

                        editor.putString("rollNo", studentRollNo)
                        editor.putString("name", studentName)
                        editor.putString("email", studentEmail)
                        editor.apply()

                        startActivity(Intent(requireActivity(),MainActivity::class.java))
                        this@ProfileLoadFragment.requireActivity().finishAffinity()


                    }

                    override fun onCancelled(error: DatabaseError) {

                        Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                    }

                })
        } catch (e: Exception) {
            Log.d("error", e.toString())
        }

    }
}