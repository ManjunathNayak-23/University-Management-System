package com.developer.aurora.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.developer.aurora.R
import com.developer.aurora.adapters.GpaBranchAdapter
import com.developer.aurora.databinding.FragmentGpaBranchBinding
import com.developer.aurora.models.GpaBranchModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class GpaBranchFragment : Fragment(), GpaBranchAdapter.onBranchClick {
    private var _binding: FragmentGpaBranchBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: GpaBranchAdapter
    private lateinit var branchList: ArrayList<GpaBranchModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGpaBranchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        branchList = ArrayList()
        adapter = GpaBranchAdapter(branchList, requireContext(), this)
        binding.branchRecyclerview.adapter = adapter
        initData()
    }

    private fun initData() {



        lifecycleScope.launch(Dispatchers.IO) {
            branchList.add(GpaBranchModel(R.drawable.cse, "cse"))
            branchList.add(GpaBranchModel(R.drawable.it, "it"))
            branchList.add(GpaBranchModel(R.drawable.ece, "ece"))
            branchList.add(GpaBranchModel(R.drawable.electronics, "eee"))
            branchList.add(GpaBranchModel(R.drawable.civil, "civil"))
            branchList.add(GpaBranchModel(R.drawable.mech, "Mechanical"))

        }
        adapter.notifyDataSetChanged()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onClick(branchName: String) {
        val action =
            GpaBranchFragmentDirections.actionGpaBranchFragmentToGpaYearFragment(branchName)
        view?.let { Navigation.findNavController(it).navigate(action) }
    }

}