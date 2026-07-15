package com.example.schedule_for_the_day.model

data class Event(
    val id: Long,
    val title: String,
    val time: String,
    val description: String)