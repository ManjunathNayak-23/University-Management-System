package com.developer.aurora.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.developer.aurora.adapters.PlacementsRecyclerAdapter
import com.developer.aurora.databinding.FragmentPlacementsBinding
import com.developer.aurora.models.Placement
import com.google.firebase.database.*


class PlacementsFragment : Fragment(), PlacementsRecyclerAdapter.OnApplyClick {
    private var _binding: FragmentPlacementsBinding? = null
    private val binding get() = _binding!!
    private var placementsList: ArrayList<Placement> = ArrayList()
    private lateinit var reference: DatabaseReference
    private lateinit var adapter: PlacementsRecyclerAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlacementsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = PlacementsRecyclerAdapter(placementsList, requireContext(), this)
        binding.semisterRecyclerview.adapter = adapter
        loadPlacementsData()
        binding.toolbar.setNavigationOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }

    }

    private fun loadPlacementsData() {


        binding.loaderLayout.visibility = View.VISIBLE
        reference = FirebaseDatabase.getInstance().reference
        reference.child("Placements")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (snapshot1: DataSnapshot in snapshot.children) {
                        val placement = snapshot1.getValue(Placement::class.java)
                        placementsList.add(placement!!)
                    }
                    try {
                        binding.loaderLayout.visibility = View.GONE
                    } catch (e: Exception) {
                        Log.d("error", e.message.toString())
                    }
                    adapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    binding.loaderLayout.visibility = View.GONE

                    Toast.makeText(
                        requireContext(),
                        error.message.toString(),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            })


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(link: String) {
        val action = PlacementsFragmentDirections.actionPlacementsFragmentToResultsWebFragment(link)
        view?.let { Navigation.findNavController(it).navigate(action) }
    }

}