package com.developer.aurora.scheduleRoom

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Schedule_table")
data class Schedule(
    @PrimaryKey(autoGenerate = true)
    val id:Int,

    val day:Int,

    val title:String,
    val startTime:String,
    val endTime :String,
    val color:String,
    val info:String


){
    var isExpanded=false
}