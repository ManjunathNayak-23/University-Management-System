package com.developer.aurora.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.developer.aurora.databinding.FragmentResultsWebBinding


class ResultsWebFragment : Fragment() {
    private var _binding: FragmentResultsWebBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResultsWebBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    val args: ResultsWebFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val link = args.resultLink
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.loadUrl(link)

        binding.toolbar.setNavigationOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

}