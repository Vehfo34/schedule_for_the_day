package com.example.schedule_for_the_day.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class Event(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val event: String,
    val time: String,
    val isCompleted: Boolean = false)