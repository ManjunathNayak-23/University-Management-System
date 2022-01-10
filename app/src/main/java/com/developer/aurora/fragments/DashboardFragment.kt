package com.developer.aurora.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.developer.aurora.R
import com.developer.aurora.activities.GpaActivity
import com.developer.aurora.activities.ScheduleActivity
import com.developer.aurora.adapters.DashboardRecyclerAdapter
import com.developer.aurora.databinding.FragmentDashboardBinding
import com.developer.aurora.models.DashBoardItemModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DashboardFragment : Fragment(), DashboardRecyclerAdapter.onItemClick {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: DashboardRecyclerAdapter
    private lateinit var list: ArrayList<DashBoardItemModel>

    private lateinit var studentRollNo: String
    private lateinit var studentName: String
    private val sharedPrefFile = "loadProfile"
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadMainImage()
        list = ArrayList()
        adapter = DashboardRecyclerAdapter(list, requireContext(), this)
        sharedPreferences = requireActivity().getSharedPreferences(
            sharedPrefFile,
            Context.MODE_PRIVATE
        )
        initData()



        studentName = sharedPreferences.getString("name", "student").toString()
        studentRollNo = sharedPreferences.getString("rollNo", "123").toString()
        binding.nameTxt.text = studentName


    }


    private fun loadMainImage() {
        Glide.with(requireContext()).load(R.drawable.ic_dashboard).skipMemoryCache(false)
            .diskCacheStrategy(
                DiskCacheStrategy.AUTOMATIC
            ).into(binding.cardImage)
    }

    private fun initData() {

        lifecycleScope.launch(Dispatchers.IO) {


            list.add(
                DashBoardItemModel(
                    R.drawable.placement,
                    "PLACEMENTS",
                    "Get info upon new placements opportunities."
                )
            )
            list.add(
                DashBoardItemModel(
                    R.drawable.attendance,
                    "ATTENDANCE",
                    "Digitally submit your attendance."
                )
            )

            list.add(
                DashBoardItemModel(
                    R.drawable.bus_track,
                    "Bus Track",
                    "Track the location of college bus."
                )
            )
            list.add(DashBoardItemModel(R.drawable.gpa, "GPA", "Calculate your semester gpa."))
            list.add(
                DashBoardItemModel(
                    R.drawable.idea,
                    "IDEA ROOM",
                    "Share,view and promote the ideas."
                )
            )

            list.add(
                DashBoardItemModel(
                    R.drawable.timetable,
                    "Time Table",
                    "Save & view your daily class schedule."
                )
            )

            list.add(
                DashBoardItemModel(
                    R.drawable.ic_exam_result,
                    "Exam Results",
                    "Semester Exam's Result links."
                )
            )
            list.add(
                DashBoardItemModel(
                    R.drawable.lecturers,
                    "LECTURERS",
                    "Basic info about your academicians."
                )
            )


        }
        binding.dashboardRecycler.adapter = adapter


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCardClick(title: String) {
        if (title == "gpa") {
            startActivity(Intent(requireActivity(), GpaActivity::class.java))

        }
        if (title == "attendance") {
            val action = DashboardFragmentDirections.actionDashboardFragmentToAttendanceFragment(
                studentName,
                studentRollNo
            )

            view?.let {
                Navigation.findNavController(it)
                    .navigate(action)
            }
        }
        if (title == "lecturers") {

            view?.let {
                Navigation.findNavController(it)
                    .navigate(R.id.action_dashboardFragment_to_lecturerListFragment)
            }
        }
        if (title == "idea room") {

            view?.let {
                Navigation.findNavController(it)
                    .navigate(R.id.action_dashboardFragment_to_ideaMenuScreenFragment)
            }
        }
        if (title == "placements") {

            view?.let {
                Navigation.findNavController(it)
                    .navigate(R.id.action_dashboardFragment_to_placementsFragment)
            }
        }
        if (title == "exam results") {

            view?.let {
                Navigation.findNavController(it)
                    .navigate(R.id.action_dashboardFragment_to_examResultsFragment)
            }
        }
        if (title == "time table") {

            view?.let {
                startActivity(Intent(requireActivity(), ScheduleActivity::class.java))
            }
        }
        if (title == "bus track") {

            view?.let {
                Navigation.findNavController(it)
                    .navigate(R.id.action_dashboardFragment_to_busSelectionFragment)
            }
        }
    }


}