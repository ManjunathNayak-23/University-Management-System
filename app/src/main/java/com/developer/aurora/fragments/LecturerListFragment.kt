package com.developer.aurora.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.developer.aurora.R
import com.developer.aurora.adapters.LecturerRecyclerAdapter
import com.developer.aurora.databinding.FragmentLecturerListBinding
import com.developer.aurora.models.Lecturer
import com.google.firebase.database.*
import java.lang.Exception

class LecturerListFragment : Fragment(), LecturerRecyclerAdapter.onLecturrerCardClick {
    private var _binding: FragmentLecturerListBinding? = null
    private val binding get() = _binding!!
    private val list: ArrayList<Lecturer> = ArrayList()
    private lateinit var adapter: LecturerRecyclerAdapter
    private lateinit var reference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLecturerListBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = LecturerRecyclerAdapter(list, requireContext(), this)
        binding.lecturerListRecycler.adapter = adapter
        initData()

        binding.toolbar.setNavigationOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }

    }

    private fun initData() {
        binding.loaderLayout.visibility = View.VISIBLE
        reference = FirebaseDatabase.getInstance().reference
        reference.child("Lecturers").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                for (snapshot1: DataSnapshot in snapshot.children) {
                    val value = snapshot1.getValue(Lecturer::class.java)
                    list.add(value!!)
                }
                try{
                    binding.loaderLayout.visibility = View.GONE
                }catch (e:Exception){
                    Log.d("e",e.message.toString())
                }

                list.sortBy { it.fullName.toString() }

                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                binding.loaderLayout.visibility = View.GONE

                Toast.makeText(requireContext(), error.message.toString(), Toast.LENGTH_SHORT)
                    .show()
            }

        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClicked(lecturer: Lecturer) {
        val action =
            LecturerListFragmentDirections.actionLecturerListFragmentToLecturerDetailFragment(
                lecturer.fullName.toString(),
                lecturer.email.toString(),
                lecturer.phoneNumber.toString(),
                lecturer.subjects.toString(),
                lecturer.branch.toString(),
                lecturer.staffRoomNumber.toString(),
                lecturer.profilePio.toString()
            )
        view?.let {
            Navigation.findNavController(it)
                .navigate(action)
        }




    }
}