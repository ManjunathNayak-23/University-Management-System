package com.developer.aurora.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.developer.aurora.adapters.ScheduleRecyclerAdapter
import com.developer.aurora.databinding.ActivityScheduleBinding
import com.developer.aurora.scheduleRoom.Schedule
import com.developer.aurora.scheduleRoom.ScheduleViewModel
import com.google.android.material.tabs.TabLayout

class ScheduleActivity : AppCompatActivity(), ScheduleRecyclerAdapter.onEditClick {

    lateinit var binding: ActivityScheduleBinding


    private lateinit var viewModel: ScheduleViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(ScheduleViewModel::class.java)

        retrieveData(binding.tabLayout.selectedTabPosition)
        binding.add.setOnClickListener {
            val intent = Intent(this, AddEditScheduleActivity::class.java)
            intent.putExtra("day", binding.tabLayout.selectedTabPosition)
            startActivity(intent)

        }



        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    retrieveData(tab.position)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        binding.back.setOnClickListener {
            this.finish()
        }
    }

    fun retrieveData(day: Int) {
        viewModel.scheduleDayWise(day).observe(this, { list ->
            if (list.isNotEmpty()) {
                binding.placeHolderImg.visibility = View.GONE
                binding.placeHolderTxt.visibility = View.GONE
            } else {
                binding.placeHolderImg.visibility = View.VISIBLE
                binding.placeHolderTxt.visibility = View.VISIBLE
            }
            binding.scheduleRecycler.adapter = ScheduleRecyclerAdapter(list, this)


        })
    }

    override fun onClick(schedule: Schedule) {


        val intent = Intent(this, AddEditScheduleActivity::class.java)
        intent.putExtra("day", schedule.day)
        intent.putExtra("id", schedule.id)
        intent.putExtra("startTime", schedule.startTime)
        intent.putExtra("endTime", schedule.endTime)
        intent.putExtra("title", schedule.title)
        intent.putExtra("color", schedule.color)
        intent.putExtra("info", schedule.info)
        startActivity(intent)
    }

}