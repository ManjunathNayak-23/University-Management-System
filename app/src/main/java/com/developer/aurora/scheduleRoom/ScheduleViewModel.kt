package com.developer.aurora.scheduleRoom

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.developer.aurora.notesRoom.NotesDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ScheduleViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ScheduleRepository

    init {
        val scheduleDao = NotesDatabase.getDatabase(application).scheduleDao()
        repository = ScheduleRepository(scheduleDao)

    }

    fun addSchedule(schedule: Schedule) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addSchedule(schedule)

        }
    }

    fun deleteSchedule(schedule: Schedule) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteSchedule(schedule)

        }
    }

    fun updateSchedule(schedule: Schedule) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateSchedule(schedule)

        }
    }

    fun scheduleDayWise(day: Int): LiveData<List<Schedule>> {


        return  repository.scheduleDayWise(day)


    }
}