package com.example.schedule_for_the_day.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.schedule_for_the_day.MyApplication
import com.example.schedule_for_the_day.model.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ScheduleViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = (application as MyApplication).database.eventDao()

    // Поток всех событий из Room (реактивный)
    private val allEventsFlow: Flow<List<Event>> = dao.observeAll()

    // Превращаем Flow в StateFlow – как в старой версии, но данные теперь из базы
    val events: StateFlow<List<Event>> = allEventsFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun addEvent(name: String, time: String) {
        viewModelScope.launch {
            dao.insert(Event(event = name, time = time))
        }
    }

    fun updateEvent(id: Long, name: String, time: String) {
        viewModelScope.launch {
            dao.update(Event(id = id, event = name, time = time))
        }
    }

    // Для экрана редактирования – по-прежнему возвращает Flow одного события
    fun getEventById(id: Long): Flow<Event?> = dao.observeById(id)
}