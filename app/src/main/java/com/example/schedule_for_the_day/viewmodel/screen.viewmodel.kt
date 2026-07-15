package com.example.schedule_for_the_day.viewmodel

import androidx.lifecycle.ViewModel
import com.example.schedule_for_the_day.model.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ScheduleViewModel : ViewModel() {
    private var nextId = 0L
    private val _events = MutableStateFlow<List<Event>>(emptyList())
    val events: StateFlow<List<Event>> = _events.asStateFlow()

    fun addEvent(title: String, time: String, description: String) {
        val newEvent = Event(id = nextId++, title = title, time = time, description = description)
        _events.value = _events.value + newEvent
    }

    fun updateEvent(id: Long, title: String, time: String, description: String) {
        _events.value = _events.value.map {
            if (it.id == id) it.copy(title = title, time = time, description = description) else it
        }
    }

    fun removeEvent(event: Event) {
        _events.value = events.value - event
    }

    fun getEventById(id: Long): Event? = _events.value.find { it.id == id }
}