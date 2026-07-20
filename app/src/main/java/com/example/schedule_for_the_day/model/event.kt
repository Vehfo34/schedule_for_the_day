package com.example.schedule_for_the_day.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class Event(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val time: String,
    val description: String)