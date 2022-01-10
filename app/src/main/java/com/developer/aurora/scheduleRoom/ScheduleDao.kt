package com.developer.aurora.scheduleRoom

import androidx.lifecycle.LiveData
import androidx.room.*
import com.developer.aurora.notesRoom.Notes

@Dao
interface ScheduleDao {



    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSchedule(schedule: Schedule)

    @Update
    suspend fun updateSchedule(schedule: Schedule)

    @Delete
    suspend fun deleteSchedule(schedule: Schedule)

    @Query("SELECT * FROM schedule_table where day=:day")
     fun scheduleDayWise(day:Int):LiveData<List<Schedule>>
}