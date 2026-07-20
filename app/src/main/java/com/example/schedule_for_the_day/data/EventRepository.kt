package com.example.schedule_for_the_day.data

import com.example.schedule_for_the_day.model.Event
import kotlinx.coroutines.flow.Flow

class EventRepository(private val dao: EventDAO) {
    fun observeAll(): Flow<List<Event>> = dao.observeAll()

    fun observeById(id: Long): Flow<Event?> = dao.observeById(id)

    suspend fun add(event: Event) = dao.insert(event)
    suspend fun update(event: Event) = dao.update(event)
    suspend fun delete(event: Event) = dao.delete(event)
}