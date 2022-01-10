package com.developer.aurora.scheduleRoom

import androidx.lifecycle.LiveData
import com.developer.aurora.notesRoom.Notes

class ScheduleRepository(private val scheduleDao: ScheduleDao){


    suspend fun addSchedule(schedule: Schedule) {
        scheduleDao.insertSchedule(schedule)
    }

    suspend fun deleteSchedule(schedule: Schedule) {
        scheduleDao.deleteSchedule(schedule)
    }
    suspend fun updateSchedule(schedule: Schedule) {
        scheduleDao.updateSchedule(schedule)
    }
     fun scheduleDayWise(day: Int):LiveData<List<Schedule>> {

        return scheduleDao.scheduleDayWise(day = day)
    }

}