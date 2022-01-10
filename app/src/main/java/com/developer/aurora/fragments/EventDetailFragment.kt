package com.developer.aurora.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.developer.aurora.databinding.FragmentEventDetailBinding


class EventDetailFragment : Fragment() {

    private var _binding: FragmentEventDetailBinding? = null
    private val binding get() = _binding!!
    private val args: EventDetailFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEventDetailBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }
        binding.eventName.text = args.eventName
        binding.eventDate.text = args.eventDate
        binding.eventVenue.text = args.eventVenue
        binding.eventCost.text = args.eventEntryFees
        binding.eventInfo.text = args.eventMoreInfo
        binding.organizersName.text = args.eventOrganizers

        Glide.with(requireActivity()).load(args.eventBannerUrl).into(binding.eventImgUrl)

        binding.phoneBtn.setOnClickListener {
            val i = Intent(Intent.ACTION_DIAL)
            val p = "tel:" + args.eventOrganizerPhoneNumber
            i.data = Uri.parse(p)
            startActivity(i)
        }
        binding.emailBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "plain/text"
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(args.eventOrganizerEmail))
            startActivity(Intent.createChooser(intent, ""))
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
    }


}