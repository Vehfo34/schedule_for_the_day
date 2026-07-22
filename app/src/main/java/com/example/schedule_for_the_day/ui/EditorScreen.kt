package com.example.schedule_for_the_day.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.schedule_for_the_day.viewmodel.ScheduleViewModel

fun parseTimeRange(timeRange: String): List<String> {
    val parts = timeRange.split(" - ")
    val start = parts.getOrElse(0) { "" }.split(":")
    val end = parts.getOrElse(1) { "" }.split(":")
    return listOf(
        start.getOrElse(0) { "" },  // startHours
        start.getOrElse(1) { "" },  // startMinutes
        end.getOrElse(0) { "" },    // endHours
        end.getOrElse(1) { "" }     // endMinutes
    )
}

@Composable
fun EditorScreen(
    eventId: Long,
    navController: NavController,
    viewModel: ScheduleViewModel = viewModel()
) {
    // Получаем событие из Room (сначала null, потом актуальное значение)
    val existingEvent by viewModel.getEventById(eventId).collectAsState(initial = null)

    // Локальные состояния (изначально пустые)
    var event by remember(eventId) { mutableStateOf("") }
    var startHours by remember(eventId) { mutableStateOf("") }
    var startMinutes by remember(eventId) { mutableStateOf("") }
    var endHours by remember(eventId) { mutableStateOf("") }
    var endMinutes by remember(eventId) { mutableStateOf("") }

    // Когда existingEvent загрузится, заполняем поля
    LaunchedEffect(existingEvent) {
        existingEvent?.let { ev ->
            event = ev.event
            val parts = parseTimeRange(ev.time)
            startHours = parts[0]
            startMinutes = parts[1]
            endHours = parts[2]
            endMinutes = parts[3]
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Событие", fontSize = 24.sp)

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = event,
                onValueChange = { event = it },
                label = { Text("Название события") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Начало", style = MaterialTheme.typography.titleSmall)
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = startHours,
                    onValueChange = { startHours = it.take(2) },
                    label = { Text("Часы") },
                    modifier = Modifier.weight(1f)
                )
                Text(":", modifier = Modifier.align(Alignment.CenterVertically), fontSize = 20.sp)
                OutlinedTextField(
                    value = startMinutes,
                    onValueChange = { startMinutes = it.take(2) },
                    label = { Text("Минуты") },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text("Конец", style = MaterialTheme.typography.titleSmall)
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = endHours,
                    onValueChange = { endHours = it.take(2) },
                    label = { Text("Часы") },
                    modifier = Modifier.weight(1f)
                )
                Text(":", modifier = Modifier.align(Alignment.CenterVertically), fontSize = 20.sp)
                OutlinedTextField(
                    value = endMinutes,
                    onValueChange = { endMinutes = it.take(2) },
                    label = { Text("Минуты") },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    val startTime = "${startHours.padStart(2, '0')}:${startMinutes.padStart(2, '0')}"
                    val endTime = "${endHours.padStart(2, '0')}:${endMinutes.padStart(2, '0')}"
                    val fullTime = "$startTime - $endTime"
                    if (existingEvent != null) {
                        viewModel.updateEvent(eventId, event, fullTime)
                    } else {
                        viewModel.addEvent(event, fullTime)
                    }
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (existingEvent != null) "Сохранить изменения" else "Добавить событие")
            }
        }
    }
}