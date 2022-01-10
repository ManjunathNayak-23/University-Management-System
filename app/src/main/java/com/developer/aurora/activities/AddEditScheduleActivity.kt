package com.developer.aurora.activities

import android.app.Activity
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.developer.aurora.databinding.ActivityAddEditScheduleBinding
import com.developer.aurora.scheduleRoom.Schedule
import com.developer.aurora.scheduleRoom.ScheduleViewModel
import java.util.*


class AddEditScheduleActivity : AppCompatActivity() {

    var color = "#FFEB3B"
    private var day = -1
    lateinit var binding: ActivityAddEditScheduleBinding
    private lateinit var viewModel: ScheduleViewModel
    private var id = -1


    var resultLauncher = registerForActivityResult(StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data

            color = data?.getStringExtra("color").toString()

            binding.colorTv.setBackgroundColor(Color.parseColor(color))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        id = intent.getIntExtra("id", -1)
        if (id != -1) {
            val title = intent.getStringExtra("title").toString()
            val startTime = intent.getStringExtra("startTime").toString()
            val endTime = intent.getStringExtra("endTime").toString()
            val info = intent.getStringExtra("info").toString()

            binding.titleEt.setText(title)
            binding.infoEt.setText(info)
            binding.startTimeTv.text = startTime
            binding.endTimeTv.text = endTime

            color = intent.getStringExtra("color").toString()
            binding.colorTv.setBackgroundColor(Color.parseColor(color))

            binding.deleteCard.visibility = View.VISIBLE

            binding.deleteCard.setOnClickListener {

                deleteData(
                    id, title, startTime, endTime, info, day, color
                )
            }
        }


        day = intent.getIntExtra("day", -1)
        viewModel = ViewModelProvider(this).get(ScheduleViewModel::class.java)
        binding.closeBtn.setOnClickListener {
            finish()
        }

        binding.startTimeTv.setOnClickListener {
            timeChooser(binding.startTimeTv)
        }
        binding.endTimeTv.setOnClickListener {
            timeChooser(binding.startTimeTv)
        }

        binding.colorTv.setOnClickListener {


            val intent = Intent(this, ColorSelectorActivity::class.java)
            resultLauncher.launch(intent)


        }
        binding.saveBtn.setOnClickListener {

            checkForMandatoryFields()
        }

    }

    private fun deleteData(
        id: Int,
        title: String,
        startTime: String,
        endTime: String,
        info: String,
        day: Int,
        color: String
    ) {
        val schedule = Schedule(id, day, title, startTime, endTime, color, info)
        viewModel.deleteSchedule(schedule)
        finish()
    }


    private fun checkForMandatoryFields() {
        val title = binding.titleEt.text.toString()
        val startTime = binding.startTimeTv.text.toString()
        val endTime = binding.endTimeTv.text.toString()
        val info = binding.infoEt.text.toString()

        if (title.isEmpty()) {
            binding.titleEt.error = "Title is Required"
        } else {
            if (day != -1) {
                if (id != -1) {

                    updateInDB(id, title, startTime, endTime, info, day, color)
                } else {
                    storeInDB(title, startTime, endTime, info, day, color)
                }


            }

        }

    }

    private fun updateInDB(
        id: Int,
        title: String,
        startTime: String,
        endTime: String,
        info: String,
        day: Int,
        color: String
    ) {
        val schedule = Schedule(id, day, title, startTime, endTime, color, info)
        viewModel.updateSchedule(schedule)
        finish()
    }

    private fun storeInDB(
        title: String,
        startTime: String,
        endTime: String,
        info: String,
        day: Int,
        color: String
    ) {
        val schedule = Schedule(0, day, title, startTime, endTime, color, info)
        viewModel.addSchedule(schedule)
        finish()
    }


    private fun timeChooser(textView: TextView) {
        val mcurrentTime: Calendar = Calendar.getInstance()
        val hour: Int = mcurrentTime.get(Calendar.HOUR_OF_DAY)
        val minute: Int = mcurrentTime.get(Calendar.MINUTE)
        val mTimePicker: TimePickerDialog = TimePickerDialog(
            this,
            { timePicker, selectedHour, selectedMinute ->
                val AM_PM: String = if (selectedHour < 12) {
                    "AM"
                } else {
                    "PM"

                }
                textView.text = "$selectedHour:$selectedMinute $AM_PM"


            },
            hour,
            minute,
            false
        ) //Yes 24 hour time

        mTimePicker.setTitle("Select Time")
        mTimePicker.show()
    }
}