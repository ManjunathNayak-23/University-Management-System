package com.developer.aurora.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.developer.aurora.adapters.GpaSemisterAdapter
import com.developer.aurora.databinding.FragmentGpaYearBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GpaYearFragment : Fragment(), GpaSemisterAdapter.onYearClick {
    private var _binding: FragmentGpaYearBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: GpaSemisterAdapter
    private lateinit var semisterList: ArrayList<String>

    private val args:GpaYearFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGpaYearBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        semisterList = ArrayList()
        adapter = GpaSemisterAdapter(semisterList,this)
        binding.semisterRecyclerview.adapter = adapter
        initData()
    }

    private fun initData() {
        lifecycleScope.launch(Dispatchers.IO) {
            semisterList.add("year 1 - semester 1")
            semisterList.add("year 1 - semester 2")
            semisterList.add("year 2 - semester 1")
            semisterList.add("year 2 - semester 2")
            semisterList.add("year 3 - semester 1")
            semisterList.add("year 3 - semester 2")
            semisterList.add("year 4 - semester 1")
            semisterList.add("year 4 - semester 2")
        }
        adapter.notifyDataSetChanged()

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onClick(sem: Int) {
        val action=GpaYearFragmentDirections.actionGpaYearFragmentToGpaCalculatorFragment(args.branchName,sem)
        view?.let { Navigation.findNavController(it).navigate(action) }
    }
}