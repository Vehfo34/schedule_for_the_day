package com.example.schedule_for_the_day.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.schedule_for_the_day.viewmodel.ScheduleViewModel

@Composable
fun EditorScreen(
    eventId: Long,
    navController: NavController,
    viewModel: ScheduleViewModel = viewModel()
) {
    val existingEvent = remember(eventId) { viewModel.getEventById(eventId) }
    var title by remember(eventId) { mutableStateOf(existingEvent?.title ?: "") }
    var time by remember(eventId) { mutableStateOf(existingEvent?.time ?: "") }
    var description by remember(eventId) { mutableStateOf(existingEvent?.description ?: "") }


    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Название события") })
        OutlinedTextField(
            value = time,
            onValueChange = { time = it },
            label = { Text("Время") })
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Описание") })

        Button(onClick = {
            if (existingEvent != null) {
                viewModel.updateEvent(eventId, title, time, description)
            } else {
                viewModel.addEvent(title, time, description)
            }
            navController.popBackStack()
        }) {
            Text(if (existingEvent != null) "Сохранить изменения" else "Добавить событие")
        }
    }
}