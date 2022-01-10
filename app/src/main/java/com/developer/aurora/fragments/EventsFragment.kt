package com.developer.aurora.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.developer.aurora.adapters.EventRecyclerAdapter
import com.developer.aurora.databinding.FragmentEventsBinding
import com.developer.aurora.models.Event
import com.google.firebase.database.*

class EventsFragment : Fragment(), EventRecyclerAdapter.OnMoreBtnClick {
    private var _binding: FragmentEventsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: EventRecyclerAdapter
    private var eventsList: ArrayList<Event> = ArrayList()
    private lateinit var reference: DatabaseReference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        _binding = FragmentEventsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = EventRecyclerAdapter(eventsList, requireContext(), this)

        binding.eventsRecyclerview.adapter = adapter
        initData()


    }

    private fun initData() {
        binding.loaderLayout.visibility = View.VISIBLE
        reference = FirebaseDatabase.getInstance().reference

        reference.child("Events").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                eventsList.clear()
                for (snapshot1: DataSnapshot in snapshot.children) {
                    val value = snapshot1.getValue(Event::class.java)
                    eventsList.add(value!!)
                }

                eventsList.reverse()
                adapter.notifyDataSetChanged()
                try {
                    binding.loaderLayout.visibility = View.GONE
                } catch (e: Exception) {
                    Log.d("error", e.toString())
                }

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

    override fun onClick(event: Event) {
        val action =
            EventsFragmentDirections.actionEventsFragmentToEventDetailFragment(
                event.eventName.toString(),
                event.venue.toString(),
                event.entryFees.toString(),
                event.eventDateAndTime.toString(),
                event.organizedBy.toString(),
                event.organizerEmail.toString(),
                event.aboutEvent.toString(),
                event.organizerPhoneNumber.toString(),
                event.eventBannerUrl.toString()
            )

        view?.let {
            Navigation.findNavController(it)
                .navigate(action)
        }
    }

}