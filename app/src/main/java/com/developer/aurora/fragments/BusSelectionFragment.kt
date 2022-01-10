package com.developer.aurora.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.developer.aurora.adapters.BusSelectionRecyclerviewAdapter
import com.developer.aurora.databinding.FragmentBusSelectionBinding


class BusSelectionFragment : Fragment(), BusSelectionRecyclerviewAdapter.OnBusCardClick {
    private var _binding: FragmentBusSelectionBinding? = null
    private val binding get() = _binding!!
    private var list: ArrayList<String> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentBusSelectionBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list.clear()
        list.add("B1")
        list.add("B2")
        list.add("B3")
        list.add("B4")

        binding.selectionRecyclerview.adapter = BusSelectionRecyclerviewAdapter(list, this)

        binding.toolbar.setNavigationOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(busName: String) {

        val action =
            BusSelectionFragmentDirections.actionBusSelectionFragmentToMapsFragment(busName)
        view?.let { Navigation.findNavController(it).navigate(action) }


    }


}