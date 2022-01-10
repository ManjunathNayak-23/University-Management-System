package com.developer.aurora.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.developer.aurora.adapters.ExamResultsRecyclerAdapter
import com.developer.aurora.databinding.FragmentExamResultsBinding
import com.developer.aurora.models.Results
import com.google.firebase.database.*

class ExamResultsFragment : Fragment(), ExamResultsRecyclerAdapter.OnItemClick {
    private var _binding: FragmentExamResultsBinding? = null
    private val binding get() = _binding!!
    private val resultsList: ArrayList<Results> = ArrayList()
    private lateinit var reference: DatabaseReference
    private lateinit var adapter: ExamResultsRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExamResultsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ExamResultsRecyclerAdapter(resultsList, this)
        binding.resultsRecyclerview.adapter = adapter
        init()
        binding.toolbar.setNavigationOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }
    }

    private fun init() {
        binding.loaderLayout.visibility = View.VISIBLE
        resultsList.clear()
        reference = FirebaseDatabase.getInstance().reference
        reference.child("Results").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                resultsList.clear()
                for (snapshot1: DataSnapshot in snapshot.children) {
                    val value = snapshot1.getValue(Results::class.java)
                    resultsList.add(value!!)
                }
                try {

                    binding.loaderLayout.visibility = View.GONE
                } catch (e: Exception) {
                    Log.d("e:", e.message.toString())
                }
                adapter.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("error", error.message.toString())
            }

        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(link: String) {
        val action =
            ExamResultsFragmentDirections.actionExamResultsFragmentToResultsWebFragment(link)
        view?.let { Navigation.findNavController(it).navigate(action) }
    }

}