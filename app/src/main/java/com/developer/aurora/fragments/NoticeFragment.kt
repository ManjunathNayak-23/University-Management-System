package com.developer.aurora.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.developer.aurora.adapters.NoticeRecyclerAdapter
import com.developer.aurora.databinding.FragmentNoticeBinding
import com.developer.aurora.models.Notice
import com.google.firebase.database.*
import java.lang.Exception

class NoticeFragment : Fragment() {

    private var _binding: FragmentNoticeBinding? = null
    private val binding get() = _binding!!
    private lateinit var noticeList: ArrayList<Notice>
    private lateinit var adapter: NoticeRecyclerAdapter
    private lateinit var reference: DatabaseReference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoticeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noticeList = ArrayList()

        adapter = NoticeRecyclerAdapter(noticeList)
        binding.noticeRecyclerview.adapter = adapter
        initNoticeData()

    }

    private fun initNoticeData() {
        binding.loaderLayout.visibility = View.VISIBLE
        reference = FirebaseDatabase.getInstance().reference
        reference.child("Notices").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                noticeList.clear()
                for (snapshot1: DataSnapshot in snapshot.children) {
                    val value = snapshot1.getValue(Notice::class.java)
                    noticeList.add(value!!)
                }

                noticeList.reverse()
                adapter.notifyDataSetChanged()
                try{
                    binding.loaderLayout.visibility = View.GONE
                }catch (e:Exception){
                    Log.d("error",e.toString())
                }


            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
            }

        })


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}