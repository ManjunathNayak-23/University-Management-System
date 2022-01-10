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
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.developer.aurora.databinding.FragmentLecturerDetailBinding


class LecturerDetailFragment : Fragment() {
    private var _binding: FragmentLecturerDetailBinding? = null
    private val binding get() = _binding!!
    private val args: LecturerDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLecturerDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.branchLec.text = args.branch
        binding.fullNameLec.text = args.fullName
        binding.staffRoomLec.text = args.staffRoomNumber
        binding.subLec.text = args.subjects
        Glide.with(requireActivity()).load(args.profilePic)
           .into(binding.circleImageView)
        binding.emailBtnLec.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "plain/text"
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(args.email))
            startActivity(Intent.createChooser(intent, ""))
        }

        binding.phoneBtnLec.setOnClickListener {
            val i = Intent(Intent.ACTION_DIAL)
            val p = "tel:" + args.phoneNumber
            i.data = Uri.parse(p)
            startActivity(i)
        }

        binding.toolbar.setNavigationOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}