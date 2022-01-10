package com.developer.aurora.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.developer.aurora.databinding.FragmentAttendanceBinding
import com.google.firebase.database.*

class AttendanceFragment : Fragment() {
    private var _binding: FragmentAttendanceBinding? = null
    private val binding get() = _binding!!
    val args: AttendanceFragmentArgs by navArgs()
    private lateinit var reference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAttendanceBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }

        binding.btnSubmit.setOnClickListener {
            val code = binding.etCode.text.toString()
            if (code.length != 13) {
                Toast.makeText(requireContext(), "Please check the code again", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            checkAndGiveAttendance(code)
        }
    }

    private fun checkAndGiveAttendance(code: String) {
        binding.loaderLayout.visibility=View.VISIBLE

        reference = FirebaseDatabase.getInstance().reference

        reference.child("Attendance").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.hasChild(code)) {
                    for (snapshot1: DataSnapshot in snapshot.children) {
                        if (snapshot1.key == code) {

                            val isLive: Boolean = snapshot1.child("isLive").value as Boolean
                            if (isLive) {
                                GiveAttendance(code)


                            } else {
                                binding.loaderLayout.visibility=View.GONE

                                Toast.makeText(
                                    requireContext(),
                                    "Attendance Closed",
                                    Toast.LENGTH_SHORT
                                ).show()

                            }
                        }
                    }

                    //GiveAttendance(code)
                } else {
                    binding.loaderLayout.visibility=View.GONE

                    Toast.makeText(
                        requireContext(),
                        "Please check the code again",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                binding.loaderLayout.visibility=View.GONE
                Toast.makeText(requireContext(), error.message.toString(), Toast.LENGTH_SHORT)
                    .show()
            }

        })

    }


    private fun GiveAttendance(code: String) {

        val map=HashMap<String,String>()
        map[args.name]=args.rollNo
        reference.child("Attendance").child(code).child("attendedList").child(args.rollNo).setValue(map).addOnCompleteListener {task->
            if(task.isSuccessful){
                binding.loaderLayout.visibility=View.GONE
                Toast.makeText(requireContext(),"Successfully Given",Toast.LENGTH_SHORT).show()

            }else{
                binding.loaderLayout.visibility=View.GONE
                Toast.makeText(requireContext(),task.exception.toString(),Toast.LENGTH_SHORT).show()

            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}